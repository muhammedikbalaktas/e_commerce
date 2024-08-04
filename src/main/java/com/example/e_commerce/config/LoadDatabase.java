package com.example.e_commerce.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.e_commerce.model.Product;
import com.example.e_commerce.model.Role;
import com.example.e_commerce.model.User;
import com.example.e_commerce.repository.ProductRepository;
import com.example.e_commerce.service.AuthenticationService;

@Configuration
public class LoadDatabase {

    private final AuthenticationService authService;
    
    public LoadDatabase(AuthenticationService authService) {
        this.authService = authService;
    }

    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository) {
        User admin =new User();
        admin.setFirstName("skow");
        admin.setLastName("s2ci");
        admin.setUsername("skow");
        admin.setPassword("skow");
        admin.setRole(Role.ADMIN);
        return args -> {
            productRepository.save(new Product("prod1", "blue",100,10));
            productRepository.save(new Product("prod2", "red",100,10));
            productRepository.save(new Product("prod3", "yellow",100,10));
            productRepository.save(new Product("prod4", "aqua",100,10));
            authService.register(admin);
        };
    }
}
