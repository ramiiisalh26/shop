package com.example.shop.payment;

import com.paypal.base.rest.PayPalRESTException;

public interface IPayment {

    <T> T createPayment(PaymentDetails paymentDetails) throws PayPalRESTException;

    <T> T executePayment(String paymentId, String payerId) throws PayPalRESTException;

    // <T> T executePaymen(String paymentId, String payerId);

}
