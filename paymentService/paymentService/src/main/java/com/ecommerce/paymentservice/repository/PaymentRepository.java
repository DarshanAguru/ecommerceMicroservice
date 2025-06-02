package com.ecommerce.paymentservice.repository;

import com.ecommerce.paymentservice.view.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByOrderId(Long orderId);
}
