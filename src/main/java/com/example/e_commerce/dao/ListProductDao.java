package com.example.e_commerce.dao;

public class ListProductDao {
    
    private Long id;

    private String name;

    private String color;

    private double price;

    private int amount;
    
    public ListProductDao(Long id, String name, String color, double price, int amount) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.price = price;
        this.amount = amount;
    }

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

    
}
