package com.genie.Ecomm.controller;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PaymentController {

    @Autowired
    private RazorpayClient razorpayClient;

    @Value("${razorpay.keySecret}")
    private String keySecret;

    // Method to create an order on Razorpay
    @PostMapping("/api/create-order")
    public String createOrder(@RequestBody Map<String, Object> data) throws RazorpayException {
        int amount = (int) data.get("amount");
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_" + System.currentTimeMillis());

        return razorpayClient.orders.create(orderRequest).toString();
    }


    @PostMapping("/api/verify-payment")
    public String verifyPayment(@RequestBody Map<String, String> data) throws RazorpayException {
        String paymentId = data.get("razorpay_payment_id");
        String orderId = data.get("razorpay_order_id");
        String signature = data.get("razorpay_signature");

        JSONObject options = new JSONObject();
        options.put("razorpay_order_id", orderId);
        options.put("razorpay_payment_id", paymentId);
        options.put("razorpay_signature", signature);

        try {
            boolean isVerified = Utils.verifyPaymentSignature(options, keySecret);
            if (isVerified) {
                // Payment successful, order status update
                System.out.println("Payment successful! Order ID: " + orderId);
                return "Payment successful";
            } else {
                // Payment failed or signature mismatch
                System.out.println("Payment verification failed! Signature mismatch.");
                return "Payment failed";
            }
        } catch (RazorpayException e) {
            // Exception handling
            return "Error while verifying signature: " + e.getMessage();
        }
    }
}