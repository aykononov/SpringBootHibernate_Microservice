package com.example.demo.controller;

import com.example.demo.dto.ProductRequest;
import com.example.demo.entity.Product;
import com.example.demo.repository.PricesRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductPriceController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PricesRepository pricesRepository;
    @PostMapping("/placeProduct")
    public Product placeProduct(@RequestBody ProductRequest request) {
        return productRepository.save(request.getProduct());
    }
    @GetMapping("/findAllProducts")
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
