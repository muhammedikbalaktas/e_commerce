package com.example.e_commerce.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_commerce.dao.UserLoginDao;
import com.example.e_commerce.dao.UserRegisterDao;
import com.example.e_commerce.model.AuthenticationResponse;
import com.example.e_commerce.model.Balance;
import com.example.e_commerce.model.Role;
import com.example.e_commerce.model.User;
import com.example.e_commerce.service.AuthenticationService;
import com.example.e_commerce.service.UserService;

@RestController
@RequestMapping("/")
public class AuthenticationController {

    private final AuthenticationService authService;


    Logger logger=LoggerFactory.getLogger(AuthenticationController.class);


    
    public AuthenticationController(AuthenticationService authService, UserService userService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register
    (@RequestBody UserRegisterDao request){
        
        User user=new User(request.getFirstName(),request.getLastName(),
        request.getUsername(),request.getPassword());
        user.setRole(Role.USER);
        Balance balance=new Balance();
        balance.setMoney(1000);
        user.setBalance(balance);
        balance.setUser(user);
        return ResponseEntity.ok(authService.register(user));
        
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> 
    login(@RequestBody UserLoginDao request){
       
        User user=new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        return ResponseEntity.ok(authService.authenticate(user));
    }
}
