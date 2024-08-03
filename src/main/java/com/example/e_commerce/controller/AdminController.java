package com.example.e_commerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @GetMapping("/hello")
    public ResponseEntity<String> helloAdmin() {
        return  ResponseEntity.ok("hello admin welcome");
    }
    
}
