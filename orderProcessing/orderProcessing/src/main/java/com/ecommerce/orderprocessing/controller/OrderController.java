package com.ecommerce.orderprocessing.controller;

import com.ecommerce.orderprocessing.service.OrderService;
import com.ecommerce.orderprocessing.view.Order;
import com.ecommerce.orderprocessing.view.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    private OrderService service;

    @PostMapping("/add")
    public ResponseEntity<?> placeOrder(@RequestBody Order order, @RequestHeader("X-User-Id") String userId) {
        try {
            Order placed = service.placeOrder(order, userId);
            if(placed == null)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return new ResponseEntity<>(placed, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to place order: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<?> getAllProducts() {
        try{
            return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch products", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getProduct/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {

        try {
            Optional<Product> product = service.getProductById(id);
            if (product.isPresent()) {
                return new ResponseEntity<>(product.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allOrders")
    public ResponseEntity<?> getAllOrders(@RequestHeader("X-User-Id") String userId) {
        try {
            return new ResponseEntity<>(service.getAllOrders(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch orders", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id, @RequestHeader("X-User-Id") String userId) {
        try {
            Optional<Order> order = service.getOrderById(id, userId);
            if (order.isPresent()) {
                return new ResponseEntity<>(order.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody Map<String, Object> updates, @RequestHeader("X-User-Id") String userId) {
        try {
            Order updated = service.updateOrder(id, updates, userId);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating order: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id, @RequestHeader("X-User-Id") String userId) {
        try {
            service.deleteOrder(id, userId);
            return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
