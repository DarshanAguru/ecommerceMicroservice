package com.ecommerce.paymentservice.controller;

import com.ecommerce.paymentservice.repository.PaymentRepository;
import com.ecommerce.paymentservice.service.PaymentService;
import com.ecommerce.paymentservice.view.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @GetMapping("/bill/{orderId}")
    public ResponseEntity<?> generateBill(@PathVariable Long orderId) {
        try {
            Payment bill = paymentService.generateBill(orderId);
            return ResponseEntity.ok(bill);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Bill generation failed: " + e.getMessage());
        }

    }

    @PostMapping("/makePayment/{orderId}")
    public ResponseEntity<?> makePayment(@PathVariable Long orderId, @RequestParam String method) {
        try {
            Payment paid = paymentService.makePayment(orderId, method);
            return ResponseEntity.ok(paid);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Payment failed: " + e.getMessage());
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getPayment(@PathVariable Long orderId) {
        try {
            return ResponseEntity.ok(paymentService.getPaymentByOrderId(orderId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Fetch failed: " + e.getMessage());
        }
    }


}
