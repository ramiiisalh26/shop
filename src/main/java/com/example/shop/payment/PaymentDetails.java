package com.example.shop.payment;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@Embeddable
@EqualsAndHashCode
public class PaymentDetails {

    // paypal info
    private double price;
	private String currency;
	private String method;
	private String intent;
	private String description;

    
    // Stripe info

    // private String paymentMethod;
    // private String paymentStatus;
    // private String transactionId;

}
