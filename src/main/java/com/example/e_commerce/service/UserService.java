package com.example.e_commerce.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.e_commerce.dao.ListProductDao;
import com.example.e_commerce.dao.ListUserDao;
import com.example.e_commerce.model.Order;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.model.Role;
import com.example.e_commerce.model.User;
import com.example.e_commerce.repository.ProductRepository;
import com.example.e_commerce.repository.UserRepository;


@Service
public class UserService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    private final JwtService jwtService;
    

    

    public UserService(ProductRepository productRepository, UserRepository userRepository, JwtService jwtService) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
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
            
            if(user.getRole()==Role.USER){
                usersDao.add(new ListUserDao(user.getId(),user.getFirstName(),
                user.getLastName(),user.getUsername(),!user.isNotBlocked()));
            
            }
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
    public void serviceRemoveProductfromBasket(Long id, String token){
        User user=getUser(token);
        List<Product> products=user.getBasket();
        for (Product product : products) {
            if(product.getId()==id){
                products.remove(product);
                return;
            }
        }
        userRepository.save(user);
    }

    @Transactional
    public boolean serviceGenerateOrder(String token){
        User user= getUser(token);
        boolean status=generateOrder(user);
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

    @Transactional(readOnly = true)
    public List<ListProductDao> serviceListBasket(String token){

        User user=getUser(token);
        List<ListProductDao> result=new ArrayList<>();
        List<Product> basket=user.getBasket();
        for (Product product : basket) {
            result.add(new ListProductDao(product.getId(), 
            product.getName(), product.getColor(), product.getPrice(), product.getAmount()));

        }
        return result;
    }
    
    public boolean generateOrder(User user){
        double price=0;
        List<Product> basket=user.getBasket();
        if(basket.isEmpty()){
            System.out.println("basket is empty");
            return false;
        }
        for (Product product : basket) {
            price+=product.getPrice();
        }

        if(price>user.getBalance().getMoney())return false;

        user.getBalance().setMoney(user.getBalance().getMoney()-price);
        Order order=new Order();
        for (Product product : basket) {
            
            if(product.getAmount()>0){
                order.getProducts().add(product);
                product.setAmount(product.getAmount()-1);
                productRepository.save(product);
                System.out.println("product amount is "+product.getAmount());
            }
            
        }
        order.setTotalPrice(price);
        basket.clear();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        user.getOrders().add(order);

        return true;
    }

    @Transactional(readOnly = true)
    public List<ListProductDao> serviceListBasketForAdmin(Long id){

        User user=userRepository.findById(id).get();
        List<ListProductDao> result=new ArrayList<>();
        List<Product> basket=user.getBasket();
        for (Product product : basket) {
            result.add(new ListProductDao(product.getId(), 
            product.getName(), product.getColor(), 
            product.getPrice(), product.getAmount()));

        }
        return result;
    }

    @Transactional
    public ListUserDao serviceGetOneUserForAdmin(Long id){
        User user=userRepository.findById(id).get();

        ListUserDao listUserDao=new ListUserDao(user.getId(), user.getFirstName()
        , user.getLastName(), user.getUsername(),!user.isNotBlocked());
        return listUserDao;

    }

    
    
}
