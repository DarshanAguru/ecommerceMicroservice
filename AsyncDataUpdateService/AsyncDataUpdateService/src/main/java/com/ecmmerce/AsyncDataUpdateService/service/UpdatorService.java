package com.ecmmerce.AsyncDataUpdateService.service;

import com.ecmmerce.AsyncDataUpdateService.models.Order;
import com.ecmmerce.AsyncDataUpdateService.models.Product;
import com.ecmmerce.AsyncDataUpdateService.repos.OrderRepository;
import com.ecmmerce.AsyncDataUpdateService.repos.ProductRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class UpdatorService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;


    @KafkaListener(topics = "payment-event", groupId = "payment-group")
    public void updateData(ConsumerRecord<String, String> record, Acknowledgment ack)
    {
        try{
            String orderId = record.value();
            Order order = orderRepository.findById(Long.parseLong(orderId)).orElse(null);
            if(order == null)
            {
                System.out.println("Order not found for ID: " + orderId);
                ack.acknowledge();
                return;
            }
            order.setStatus("SUCCESS");
            Long productId = order.getProductId();
            Product prod = productRepository.findById(productId).orElse(null);
            if(prod == null)
            {
                System.out.println("Product not found for ID: " + productId);
                ack.acknowledge();
                return;
            }
            if(prod.getQuantity() < order.getQuantity())
            {
                System.out.println("Insufficient stock for Product ID: " + productId);
                ack.acknowledge();
                return;
            }
            prod.setQuantity(prod.getQuantity() - order.getQuantity());
            orderRepository.save(order);
            productRepository.save(prod);
            ack.acknowledge();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

}
