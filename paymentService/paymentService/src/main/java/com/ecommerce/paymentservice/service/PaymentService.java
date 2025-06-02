package com.ecommerce.paymentservice.service;

import com.ecommerce.paymentservice.dto.OrderDTO;
import com.ecommerce.paymentservice.dto.ProductDTO;
import com.ecommerce.paymentservice.repository.PaymentRepository;
import com.ecommerce.paymentservice.view.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private RestTemplate restTemplate;
    public Payment generateBill(Long orderId)
    {


        HttpHeaders headers = new HttpHeaders();
        headers.set("X-InterService-Id", "payment-service");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<OrderDTO> orderResponse = restTemplate.exchange(
                "http://localhost:9002/api/v1/order/getOrder/" + orderId,
                HttpMethod.GET,
                entity,
                OrderDTO.class);
        OrderDTO order = orderResponse.getBody();

        ResponseEntity<ProductDTO> productResponse = restTemplate.exchange(
                "http://localhost:9001/api/v1/product/getProduct/" + order.getProductId(),
                HttpMethod.GET,
                entity,
                ProductDTO.class);
        ProductDTO product = productResponse.getBody();

        double amount = order.getQuantity() * product.getPrice();
        double gst = amount * 0.18;
        double total = amount + gst;

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setGst(gst);
        payment.setTotalAmount(total);
        payment.setStatus("UNPAID");

        return paymentRepository.save(payment);
    }
    public Payment makePayment(Long orderId, String method) {
        Payment p = paymentRepository.findByOrderId(orderId);
        p.setStatus("PAID");
        p.setPaymentMethod(method);
        return paymentRepository.save(p);
    }
    public Payment getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

}
