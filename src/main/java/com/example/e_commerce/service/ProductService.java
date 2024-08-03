package com.example.e_commerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.e_commerce.model.Product;
import com.example.e_commerce.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product serviceGetProductById(Long id){

        return productRepository.findById(id).get();
    }
}
