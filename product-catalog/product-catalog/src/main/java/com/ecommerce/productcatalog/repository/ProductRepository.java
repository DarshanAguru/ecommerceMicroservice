package com.ecommerce.productcatalog.repository;

import com.ecommerce.productcatalog.view.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository <Product,Long>{
}
