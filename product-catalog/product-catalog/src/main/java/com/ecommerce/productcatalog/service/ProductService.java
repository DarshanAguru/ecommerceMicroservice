package com.ecommerce.productcatalog.service;


import com.ecommerce.productcatalog.repository.ProductRepository;
import com.ecommerce.productcatalog.view.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public Product saveProduct(Product product)
    {
        return productRepository.save(product);
    }
    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }
    public Product getProductById(Long id)
    {
        return productRepository.findById(id).orElse(null);
    }

    public Product updateProduct(Long id, Map<String, Object> updates) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Product not found"));

        if (updates.containsKey("name")) product.setName((String) updates.get("name"));
        if (updates.containsKey("description")) product.setDescription((String) updates.get("description"));
        if (updates.containsKey("price")) product.setPrice(Double.parseDouble(updates.get("price").toString()));
        if (updates.containsKey("quantity")) product.setQuantity(Integer.parseInt(updates.get("quantity").toString()));

        return productRepository.save(product);
    }
    public boolean deleteProduct(Long id)
    {
        if(productRepository.existsById(id))
        {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
