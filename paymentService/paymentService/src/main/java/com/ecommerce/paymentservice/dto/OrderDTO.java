package com.ecommerce.paymentservice.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private Long id;
    private String customerName;
    private Long productId;
    private int quantity;
    private String status;

}
