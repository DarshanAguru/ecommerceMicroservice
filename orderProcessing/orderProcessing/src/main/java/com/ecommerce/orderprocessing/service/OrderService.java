package com.ecommerce.orderprocessing.service;
import com.ecommerce.orderprocessing.repository.OrderRepository;
import com.ecommerce.orderprocessing.repository.ProductRepository;
import com.ecommerce.orderprocessing.view.Order;
import com.ecommerce.orderprocessing.view.Product;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order placeOrder(Order order, String userId)
    {
        try{
            UUID uId = UUID.fromString(userId);
            Product prod = productRepository.findById(order.getProductId()).orElse(null);
            if(prod == null)
            {
                return null; // Product not found
            }
            if(prod.getQuantity() < order.getQuantity())
            {
                return null; // Insufficient stock
            }
            order.setUserId(uId);
            order.setStatus("PENDING");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return null; // Error occurred
        }
        return orderRepository.save(order);
    }
    public List<Product> getAllProducts() {
       return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    public List<Order> getAllOrders(String userId) {
        UUID uId = UUID.fromString(userId);
        return orderRepository.findAllByUserId(uId);
    }
    public Optional<Order> getOrderById(Long id, String userId) {
        UUID uId = UUID.fromString(userId);
        return orderRepository.findByIdAndUserId(id,uId);
    }
    public Order updateOrder(Long id, Map<String, Object> updates, String userId) {
        UUID uId = UUID.fromString(userId);
        Order order = orderRepository.findByIdAndUserId(id, uId).orElseThrow(() -> new NoSuchElementException("Order not found"));

        if (updates.containsKey("quantity"))
            order.setQuantity((int) updates.get("quantity"));
        if (updates.containsKey("status"))
            order.setStatus((String) updates.get("status"));


        return orderRepository.save(order);
    }
    public void deleteOrder(Long id, String userId) {
        UUID uId = UUID.fromString(userId);
        if (!orderRepository.existsByIdAndUserId(id, uId)) {
            throw new NoSuchElementException("Order not found");
        }
        orderRepository.deleteById(id);
    }

}
