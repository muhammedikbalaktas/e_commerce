package com.example.e_commerce.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    
    @Enumerated(value = EnumType.STRING)
    private Role role;

    


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "basket",
        joinColumns=@JoinColumn(name="user_id"),
        inverseJoinColumns = @JoinColumn(name="product_id")
    )
    @JsonManagedReference
    private Set<Product> basket=new HashSet<>();


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonManagedReference
    private List<Order> orders=new ArrayList<>();


    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonManagedReference
    private Balance balance;

    
    public void addProduct(Product product){
        this.basket.add(product);
    }

    public void removeProduct(Product product){
        this.basket.remove(product);
    }

    public boolean generateOrder(){
        double price=0;

        if(basket.isEmpty()){
            System.out.println("basket is empty");
            return false;
        }
        for (Product product : basket) {
            price+=product.getPrice();
        }

        if(price>this.getBalance().getMoney())return false;

        this.getBalance().setMoney(this.getBalance().getMoney()-price);
        Order order=new Order();
        for (Product product : basket) {
            
            order.getProducts().add(product);
            product.setAmount(product.getAmount()-1);
            
        }
        order.setTotalPrice(price);
        basket.clear();
        orders.add(order);

        return true;
    }

   

    





    /*
     * CONFIG
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        return List.of(new SimpleGrantedAuthority(role.name()));
    
    }
    public User(){}
    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        

        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
       
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        
        return true;
    }


    @Override
    public boolean isEnabled() {
        
        return true;
    }


    //getters and setters

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    


    public Set<Product> getBasket() {
        return basket;
    }

    public void setBasket(Set<Product> basket) {
        this.basket = basket;
    }

    

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    

    

    


    
    
}
