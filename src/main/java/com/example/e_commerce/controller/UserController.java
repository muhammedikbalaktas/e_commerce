package com.example.e_commerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_commerce.dao.IdDao;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.service.ProductService;
import com.example.e_commerce.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RequestMapping("/user")
@RestController
public class UserController {
    

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    

    @PostMapping("/add-product")
    public ResponseEntity<String> addProduct(
        @RequestHeader("Authorization") String authorizationHeader,
        @RequestBody IdDao idDao
        ){
            Product product=productService.serviceGetProductById(idDao.getId());
            String token = authorizationHeader.substring(7); 
            userService.serviceAddProductToBasket(product, token);
            

            
        return ResponseEntity.ok("success");
    }

    @PostMapping("/generate-order")
    public ResponseEntity<String> generateOrder(

    @RequestHeader("Authorization") String authorizationHeader
    ){
        String token = authorizationHeader.substring(7); 
        boolean result=userService.serviceGenerateOrder(token);
        if(!result)return ResponseEntity.ok("fail");
        return ResponseEntity.ok("success");
    }


    
}
