package com.example.e_commerce.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.e_commerce.model.Product;
import com.example.e_commerce.repository.ProductRepository;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository) {
        return args -> {
            productRepository.save(new Product("prod1", "blue",100,10));
            productRepository.save(new Product("prod2", "red",100,10));
            productRepository.save(new Product("prod3", "yellow",100,10));
            productRepository.save(new Product("prod4", "aqua",100,10));
            
        };
    }
}
