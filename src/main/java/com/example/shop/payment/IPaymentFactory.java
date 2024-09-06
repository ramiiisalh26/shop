package com.example.shop.payment;

public interface IPaymentFactory {
    IPayment getPayment(String paymentType);
}
