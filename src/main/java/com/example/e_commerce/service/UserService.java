package com.example.e_commerce.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.e_commerce.dao.ListUserDao;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.model.User;
import com.example.e_commerce.repository.UserRepository;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    private final JwtService jwtService;
    

    public UserService(JwtService jwtService) {
        this.jwtService=jwtService;
    }

    @Transactional
    public void createUser(User user){
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Set<ListUserDao> serviceGetAllUsers(){
        
        Set<ListUserDao> usersDao=new HashSet<>();
        List<User> users=
        userRepository.findAll();

        for (User user : users) {
            usersDao.add(new ListUserDao(user.getFirstName(),user.getLastName(),user.getUsername()));
        }

        return usersDao;
        
    }

    @Transactional
    public void serviceAddProductToBasket(Product product, String token){

        User user=
        userRepository.findByUsername(jwtService.extractUsername(token)).get();
        user.addProduct(product);
        
        userRepository.save(user);
    }

    @Transactional
    public void serviceRemoveProductfromBasket(Product product, String token){
        User user=getUser(token);
        user.removeProduct(product);
        userRepository.save(user);
    }

    @Transactional
    public boolean serviceGenerateOrder(String token){
        User user= getUser(token);
        boolean status=user.generateOrder();
        userRepository.save(user);
        return status;
    }

    @Transactional
    public void serviceAddMoney(String token, double amount){
        User user =getUser(token);
        user.getBalance().setMoney(user.getBalance().getMoney()+amount);

        userRepository.save(user);

    }

    public User getUser(String token){
        
        return userRepository.findByUsername(jwtService.extractUsername(token)).get();
        
    }
    


    
    
}
