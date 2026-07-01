package com.klef.fsad.sdp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {

    private String payerName;

    private String payerEmail;

    private Double amount;

    private String currency;

    private String description;

}