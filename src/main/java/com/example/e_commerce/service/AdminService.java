package com.example.e_commerce.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.e_commerce.dao.ListProductDao;
import com.example.e_commerce.dao.ListUserDao;
import com.example.e_commerce.model.User;
import com.example.e_commerce.repository.ProductRepository;
import com.example.e_commerce.repository.UserRepository;

import jakarta.persistence.Id;

@Service
public class AdminService {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final UserService userService;

    private final ProductService productService;


   
    
    public AdminService(UserRepository userRepository, ProductRepository productRepository, UserService userService,
            ProductService productService) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.userService = userService;
        this.productService = productService;
    }




    public Set<ListUserDao> getAllUsers(){

        return userService.serviceGetAllUsers();
        
    }

    public ListUserDao getOneUser(Long id){
        return userService.serviceGetOneUserForAdmin(id);
    }
    
    public List<ListProductDao> listBasketForUser(Long id){
        
        return userService.serviceListBasketForAdmin(id);
    }

    @Transactional
    public void removeUser(Long id){
        userRepository.deleteById(id);
    }

    @Transactional
    public void banUser(Long id){

        User user =userRepository.findById(id).get();

        user.setNotBlocked(false);

        userRepository.save(user);
        
    }
    @Transactional
    public void removeBanUser(Long id){

        User user =userRepository.findById(id).get();

        user.setNotBlocked(true);

        userRepository.save(user);
        
    }
    
}
