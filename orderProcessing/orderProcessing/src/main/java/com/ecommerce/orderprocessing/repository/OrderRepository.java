package com.ecommerce.orderprocessing.repository;

import com.ecommerce.orderprocessing.view.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findAllByUserId(UUID userId);
    public Optional<Order> findByIdAndUserId(Long id, UUID userId);
    public boolean existsByIdAndUserId(Long id, UUID userId);
}
