package com.klef.fsad.sdp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@Entity
@Table(name = "payment_table")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(nullable = false, unique = true)
    private String stripeSessionId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String payerName;

    @Column(nullable = false)
    private String payerEmail;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String currency;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private String paymentStatus;

    @Column
    private String paymentMethod;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime paidAt;



    @Override
    public String toString() {
        return "Payment [paymentId=" + paymentId +
                ", stripeSessionId=" + stripeSessionId +
                ", username=" + username +
                ", role=" + role +
                ", payerName=" + payerName +
                ", payerEmail=" + payerEmail +
                ", amount=" + amount +
                ", currency=" + currency +
                ", description=" + description +
                ", paymentStatus=" + paymentStatus +
                ", paymentMethod=" + paymentMethod +
                ", createdAt=" + createdAt +
                ", paidAt=" + paidAt + "]";
    }
}