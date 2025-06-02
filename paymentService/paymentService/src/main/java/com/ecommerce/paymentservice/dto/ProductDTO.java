package com.ecommerce.paymentservice.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private double price;
    private int quantity;
}
