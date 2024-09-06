// package com.example.shop.payment;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.paypal.api.payments.Links;
// import com.paypal.api.payments.Payment;
// import com.paypal.base.rest.PayPalRESTException;

// @Service
// public class PaymentService {

//     private final IPaymentFactory iPaymentFactory;

//     @Autowired
//     public PaymentService(final IPaymentFactory iPaymentFactory) {
//         this.iPaymentFactory = iPaymentFactory;
//     }

//     public Payment paymentPaypal(PaymentDetails paymentDetails) throws PayPalRESTException{
//         // System.out.println(paymentDetails);
//         IPayment iPayment = iPaymentFactory.getPayment(paymentDetails.getMethod());
//         // System.out.println(iPayment);
//         if (paymentDetails.getMethod().equals("paypal")) {
//             // System.out.println(paymentDetails);
//             Payment payment = iPayment.createPayment(paymentDetails);
            
//             System.out.println("Tamer");
//             return payment;      
//         }

//         return null;
//     }

//     // public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
//     //     // Payment payment = new Payment();
//     //     // IPayment iPayment = iPaymentFactory.getPayment("paypal");
//     //     // return iPayment.executePayment(paymentId, payerId);
//     // }

// }
