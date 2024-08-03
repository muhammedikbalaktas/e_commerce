package com.example.e_commerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_commerce.dao.IdDao;
import com.example.e_commerce.dao.ListProductDao;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.service.ProductService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/product")
public class ProductController {
    
    private final ProductService productService;
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<Product> getProduct(@RequestBody IdDao id) {
        

        return ResponseEntity.ok(productService.serviceGetProductById(id.getId()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ListProductDao>> getAllProducts() {
        return  ResponseEntity.ok(productService.serviceGetAllProducts());
    }
    
    
}
