package com.ecommerce.productcatalog.repository;

import com.ecommerce.productcatalog.view.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository <Product,Long>{
    List<Product> findAllByUserId(UUID userId);
    Optional<Product> findByIdAndUserId(Long id, UUID uid);
    boolean existsByIdAndUserId(Long id, UUID uid);
}
