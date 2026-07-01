package com.klef.fsad.sdp.services;

import com.klef.fsad.sdp.dto.PaymentRequestDTO;
import com.klef.fsad.sdp.dto.PaymentResponseDTO;
import com.klef.fsad.sdp.model.Payment;
import com.stripe.exception.StripeException;

public interface PaymentService {

    PaymentResponseDTO createCheckoutSession(PaymentRequestDTO request, String username, String role) throws StripeException;

    Payment updateSuccessfulPayment(String sessionId) throws StripeException;

    Payment updateCancelledPayment(String sessionId);
}
