package com.example.e_commerce.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String color;

    private double price;

    private int amount;
    public Product(){}

    public Product(String name, String color, double price, int amount) {
        this.name = name;
        this.color = color;
        this.price = price;
        this.amount = amount;
    }


    @ManyToMany(mappedBy = "basket")
    @JsonBackReference
    private Set<User> users=new HashSet<>();


    @ManyToMany(mappedBy = "products")
    @JsonBackReference
    private Set<Order> orders=new HashSet<>();




   /*
    * GETTERS AND SETTERS
    */

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getColor() {
        return color;
    }


    public void setColor(String color) {
        this.color = color;
    }


    public double getPrice() {
        return price;
    }


    public void setPrice(double price) {
        this.price = price;
    }


    public int getAmount() {
        return amount;
    }


    public void setAmount(int amount) {
        this.amount = amount;
    }


    public Set<User> getUsers() {
        return users;
    }


    public void setUsers(Set<User> users) {
        this.users = users;
    }


    public Set<Order> getOrders() {
        return orders;
    }


    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }


    

     
    
}
