package com.klef.fsad.sdp.services;

import com.klef.fsad.sdp.dto.PaymentRequestDTO;
import com.klef.fsad.sdp.dto.PaymentResponseDTO;
import com.klef.fsad.sdp.model.Payment;
import com.klef.fsad.sdp.repository.PaymentRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public PaymentResponseDTO createCheckoutSession(PaymentRequestDTO request, String username, String role) throws StripeException {

        /*
         * -----------------------------
         * Create Product Information
         * -----------------------------
         */

        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData
                                                                        .builder()
                                                                        .setName(request.getDescription())
                                                                        .build();

        /*
         * -----------------------------
         * Create Price Data
         * Stripe accepts amount in smallest currency unit.
         *
         * Example:
         * INR
         * 500 Rupees = 50000 Paisa
         *
         * USD
         * 20 Dollar = 2000 Cents
         * -----------------------------
         */

        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData
                                                            .builder()
                                                            .setCurrency(request.getCurrency().toLowerCase())
                                                            .setUnitAmount((long) (request.getAmount() * 100))
                                                            .setProductData(productData)
                                                            .build();

        /*
         * -----------------------------
         * Create Line Item
         * -----------------------------
         */

        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem
                                                .builder()
                                                .setQuantity(1L)
                                                .setPriceData(priceData)
                                                .build();

        /*
         * -----------------------------
         * Create Checkout Session
         * -----------------------------
         */

        SessionCreateParams params = SessionCreateParams.builder()
                                    .setMode(SessionCreateParams.Mode.PAYMENT)
                                    .setSuccessUrl("http://localhost:8080/payment/success?session_id={CHECKOUT_SESSION_ID}")
                                    .setCancelUrl("http://localhost:8080/payment/cancel?session_id={CHECKOUT_SESSION_ID}")
                                    .addLineItem(lineItem)
                                    .build();

        /*
         * -----------------------------
         * Create Stripe Session
         * -----------------------------
         */

        Session session = Session.create(params);

        /*
         * -----------------------------
         * Save Payment into Database
         * -----------------------------
         */

        Payment payment = new Payment();

        payment.setStripeSessionId(session.getId());
        payment.setUsername(username);
        payment.setRole(role);
        payment.setPayerName(request.getPayerName());
        payment.setPayerEmail(request.getPayerEmail());
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setDescription(request.getDescription());
        payment.setPaymentStatus("PENDING");
        payment.setCreatedAt(LocalDateTime.now());

        paymentRepository.save(payment);

        /*
         * -----------------------------
         * Prepare Response
         * -----------------------------
         */

        PaymentResponseDTO response = new PaymentResponseDTO();

        response.setMessage("Checkout Session Created Successfully");
        response.setStripeSessionId(session.getId());
        response.setCheckoutUrl(session.getUrl());
        response.setAmount(request.getAmount());
        response.setCurrency(request.getCurrency());
        response.setPaymentStatus("PENDING");

        return response;
    }

    @Override
    public Payment updateSuccessfulPayment(String sessionId) throws StripeException {

        Session session = Session.retrieve(sessionId);

        Optional<Payment> optionalPayment = paymentRepository.findByStripeSessionId(sessionId);

        if (optionalPayment.isEmpty()) {
            throw new RuntimeException("Payment Record Not Found");
        }

        Payment payment = optionalPayment.get();

        if ("paid".equalsIgnoreCase(session.getPaymentStatus())) {

            payment.setPaymentStatus("SUCCESS");
            payment.setPaidAt(LocalDateTime.now());

            if (session.getPaymentIntent() != null) {
                payment.setPaymentMethod(
                        session.getPaymentIntent()
                );
            }

            paymentRepository.save(payment);
        }

        return payment;
    }

    @Override
    public Payment updateCancelledPayment(String sessionId) {

        Optional<Payment> optionalPayment =
                paymentRepository.findByStripeSessionId(sessionId);

        if (optionalPayment.isEmpty()) {
            throw new RuntimeException("Payment Record Not Found");
        }

        Payment payment = optionalPayment.get();

        payment.setPaymentStatus("CANCELLED");

        paymentRepository.save(payment);

        return payment;
    }

}