package com.genie.Ecomm.service;

import com.genie.Ecomm.model.Product;
import com.genie.Ecomm.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
       return productRepository.findAll();
    }

    public Product getProductById(Long id) {

    return   productRepository.findById(id).orElse(null);
    }
 // single product add
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // ✅ multiple products add
    public List<Product> addProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }


    public void deletProduct(Long id) {
        productRepository.deleteById(id);
    }
}
