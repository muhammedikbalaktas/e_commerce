package com.example.e_commerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_commerce.dao.IdDao;
import com.example.e_commerce.dao.ListProductDao;
import com.example.e_commerce.dao.ListUserDao;
import com.example.e_commerce.service.AdminService;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/admin")
public class AdminController {
    
    private final AdminService adminService;

    
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/all-users")

    public ResponseEntity<Set<ListUserDao>> getAllUsers(){

        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PostMapping("/user")

    public ResponseEntity<ListUserDao> getOneUser(
        @RequestBody IdDao id
    ){

        return ResponseEntity.ok(adminService.getOneUser(id.getId()));
        
    }

    @PostMapping("/basket")

    public ResponseEntity<List<ListProductDao>> getOneUserBasket(
        @RequestBody IdDao id
    ){

        return ResponseEntity.ok(adminService.listBasketForUser(id.getId()));
        
    }

    @DeleteMapping("/user")

    public ResponseEntity<String> deleteUser(
        @RequestBody IdDao id
    ){
        adminService.removeUser(id.getId());
        return ResponseEntity.ok("Success");
        
    }

    @PostMapping("/ban")

    public ResponseEntity<String> banUser(
        @RequestBody IdDao id
    ){

        adminService.banUser(id.getId());
        return ResponseEntity.ok("Success");
        
    }

    @PostMapping("/remove-ban")

    public ResponseEntity<String> removeBanUser(
        @RequestBody IdDao id
    ){

        adminService.removeBanUser(id.getId());
        return ResponseEntity.ok("Success");
        
    }





    
}
