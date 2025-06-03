package com.ecmmerce.AsyncDataUpdateService.repos;


import com.ecmmerce.AsyncDataUpdateService.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
