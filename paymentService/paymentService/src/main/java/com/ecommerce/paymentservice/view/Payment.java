package com.ecommerce.paymentservice.view;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter@Getter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private double amount;
    private double gst;
    private double totalAmount;
    private String paymentMethod;
    private String status;

}
