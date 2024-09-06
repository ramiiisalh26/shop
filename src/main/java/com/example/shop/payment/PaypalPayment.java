package com.example.shop.payment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PaypalPayment implements IPayment{
    
    
    private final String clientId = "Adv6WbRq9WYR_03NM_gnv2yP1CaSmTktHn5d-RmfsX6sNgDp05Xx0bum3aLTqPavLz11brDMci3-kqY4";
	private final String clientSecret = "EGDa-fBAG0h3xpjtv99UD3S4HdAaew4fPRvVpakUojdwPskljKitOt7YrydMZKr-NnBCH_Kqty81o0G1";
	private final String mode = "sandbox";

    // @Autowired
    private APIContext apiContext;
    

    public PaypalPayment(final APIContext apiContext) {
        this.apiContext = apiContext;
    }

    final static String SUCCESS_URL = "http://localhost:8090/api/v1/pay/success";
    final static String CANCEL_URL = "http://localhost:8090/api/v1/pay/cancel";

    @SuppressWarnings({ "unchecked"})
    @Override
    public Payment createPayment(PaymentDetails paymentDetails) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(paymentDetails.getCurrency());
        paymentDetails.setPrice(new BigDecimal(paymentDetails.getPrice()).setScale(2,RoundingMode.HALF_UP).doubleValue());
        amount.setTotal(String.format("%.2f",paymentDetails.getPrice()));
        
        Transaction transaction = new Transaction();
        transaction.setDescription(paymentDetails.getDescription());
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(paymentDetails.getMethod());

        Payment payment = new Payment();
        payment.setIntent(paymentDetails.getIntent());
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(CANCEL_URL);
        redirectUrls.setReturnUrl(SUCCESS_URL);
        payment.setRedirectUrls(redirectUrls);

        APIContext apiContext = new APIContext(clientId, clientSecret, mode);
        Payment pay = payment.create(apiContext);

        return pay;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        try {
            return payment.execute(apiContext, paymentExecution);
        } catch (PayPalRESTException e) {
            throw new PayPalRESTException("Error Execute");
        }

    }
    
}
