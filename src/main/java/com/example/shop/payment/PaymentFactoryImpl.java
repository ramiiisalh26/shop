package com.example.shop.payment;

import org.springframework.stereotype.Component;

@Component
public class PaymentFactoryImpl implements IPaymentFactory{

    @Override
    public IPayment getPayment(String paymentType) {
        if ("PAYPAL".equalsIgnoreCase(paymentType)) {
            return new PaypalPayment();
        }
        // else if("STRIPEPAYMENT".equalsIgnoreCase(paymentType)){
        //     return new StripePayment();
        // }

        return null;
    }
    
}
