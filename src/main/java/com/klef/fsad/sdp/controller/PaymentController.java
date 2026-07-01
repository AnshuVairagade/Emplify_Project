package com.klef.fsad.sdp.controller;

import com.klef.fsad.sdp.dto.PaymentRequestDTO;
import com.klef.fsad.sdp.dto.PaymentResponseDTO;
import com.klef.fsad.sdp.model.Payment;
import com.klef.fsad.sdp.security.JWTUtilizer;
import com.klef.fsad.sdp.services.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payment")
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private JWTUtilizer jwtService;

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody PaymentRequestDTO request, @RequestHeader("Authorization") String authHeader) {

        try {

            String token = authHeader.substring(7);

            Map<String, String> jwt = jwtService.validateToken(token);

            if (!"200".equals(jwt.get("code"))) {
                return ResponseEntity.status(401).body(Map.of("message", "Invalid or Expired Token"));
            }

            String role = jwt.get("role");

            if (!(role.equals("ADMIN") || role.equals("MANAGER") || role.equals("EMPLOYEE"))) {
                return ResponseEntity.status(403).body(Map.of("message", "Access Denied"));
            }

            PaymentResponseDTO response = paymentService.createCheckoutSession(request, jwt.get("username"), role);

            return ResponseEntity.ok(response);

        } catch (StripeException e) {

            return ResponseEntity.status(500).body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/success")
    public ResponseEntity<?> paymentSuccess(@RequestParam("session_id") String sessionId) {

        try {
            Payment payment = paymentService.updateSuccessfulPayment(sessionId);
            return ResponseEntity.ok(Map.of("message", "Payment Successful", "payment", payment));
        } catch (Exception e) {

            return ResponseEntity.status(500).body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/cancel")
    public ResponseEntity<?> paymentCancelled(@RequestParam("session_id") String sessionId) {

        try {
            Payment payment = paymentService.updateCancelledPayment(sessionId);
            return ResponseEntity.ok(Map.of("message", "Payment Cancelled", "payment", payment));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", e.getMessage()));
        }
    }

}