package com.ecmmerce.AsyncDataUpdateService.repos;


import com.ecmmerce.AsyncDataUpdateService.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {
}
