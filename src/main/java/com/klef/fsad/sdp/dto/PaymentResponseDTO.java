package com.klef.fsad.sdp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {

    private String message;

    private String checkoutUrl;

    private String stripeSessionId;

    private String paymentStatus;

    private Double amount;

    private String currency;

}