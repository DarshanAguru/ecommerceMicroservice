package com.ecommerce.productcatalog.controller;
import com.ecommerce.productcatalog.service.ProductService;
import com.ecommerce.productcatalog.view.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product, @RequestHeader("X-User-Id") String userId) {
        try {
            Product saved = productService.saveProduct(product, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong while adding product.");
        }

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts(@RequestHeader("X-User-Id") String userId) {
        try {
            List<Product> products = productService.getAllProducts(userId);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to fetch products.");
        }
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
        try {
            Product product = productService.getProductById(id);
            if (product != null) {
                return ResponseEntity.ok(product);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error while fetching product.");
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductByIdAndUserId(@PathVariable Long id, @RequestHeader("X-User-Id") String userId) {
        try {
            Product product = productService.getProductByIdAndUserId(id, userId);
            if (product != null) {
                return ResponseEntity.ok(product);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error while fetching product.");
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Map<String, Object> updates, @RequestHeader("X-User-Id") String userId) {
        try {
            Product updated = productService.updateProduct(id, updates, userId);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id, @RequestHeader("X-User-Id") String userId) {
        try {
            boolean isDeleted = productService.deleteProduct(id, userId);
            if (isDeleted) {
                return ResponseEntity.ok("Product Deleted Successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error while deleting product.");
        }
    }

}
