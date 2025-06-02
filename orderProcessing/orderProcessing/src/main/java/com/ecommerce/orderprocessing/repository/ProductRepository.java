package com.ecommerce.orderprocessing.repository;


import com.ecommerce.orderprocessing.view.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
