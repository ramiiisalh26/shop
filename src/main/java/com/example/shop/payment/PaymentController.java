package com.example.shop.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
@RequestMapping("/api/v1/payment")
public class PaymentController {
    
    public static final String SUCCESS = "pay/success";
    public static final String CANCEL = "pay/cancel";

    // final static String _URL = "http://localhost:8090/api/v1/pay";

    private final IPaymentFactory iPaymentFactory;
    
    @Autowired
    public PaymentController(IPaymentFactory iPaymentFactory) {
        this.iPaymentFactory = iPaymentFactory;
    }
// @RequestParam final String payType
    @GetMapping("/")
    public String home(){
        return "home";
    }

    @PostMapping("/pay")
    public String Payment(@ModelAttribute("paymentDetails") final PaymentDetails paymentDetails) throws PayPalRESTException{

        if (paymentDetails.getMethod().equals("paypal")) {

            IPayment pay = iPaymentFactory.getPayment("paypal");
            Payment payment = pay.createPayment(paymentDetails);

            for(Links link: payment.getLinks()){
                if (link.getRel().equals("approval_url")) {
                    return "redirect:" + link.getHref();
                }
            }                
        }
        return "redirect:";                        
    }

    @GetMapping(value = CANCEL)
    public String cancelPay() {
        return "cancel";
    }

    @GetMapping(value = SUCCESS)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){

        try {
	            IPayment pay = iPaymentFactory.getPayment("paypal");
                Payment payment = pay.executePayment(paymentId, payerId);
	            System.out.println(payment.toJSON());
	            if (payment.getState().equals("approved")) {
	                return "success";
	            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
	        return "redirect:/";
    }
}
