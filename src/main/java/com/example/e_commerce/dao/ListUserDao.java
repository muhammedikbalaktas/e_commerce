package com.example.e_commerce.dao;

public class ListUserDao {
    private Long id;
    
    private String firstName;
    private String lastName;
    
    private String username;

    private boolean isBanned;
    

    

    public ListUserDao(Long id, String firstName, String lastName, String username, boolean isBanned) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.isBanned = isBanned;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    

}
