package com.example.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.e_commerce.model.Product;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product,Long> {
    
    Optional<Product>  findById(Long id);
} 
