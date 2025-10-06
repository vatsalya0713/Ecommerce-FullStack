package com.genie.Ecomm.controller;

import com.genie.Ecomm.model.Product;
import com.genie.Ecomm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductCobtroller {

    @Autowired
    private ProductService productService;


    @GetMapping
    List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
    @GetMapping("/{id}")
    public  Product getProductById(@PathVariable Long id)
    {
        return  productService.getProductById(id);
    }

    @PostMapping("/addProduct")
    public  Product addProduct (@RequestBody Product product){
        return productService.addProduct(product);
    }


    @PostMapping("/addProducts")
    public List<Product> addProducts(@RequestBody List<Product> products) {
        return productService.addProducts(products);
    }

    @DeleteMapping("/{id}")
    public void deletProduct(@PathVariable Long id){
        productService.deletProduct(id);
    }
}
