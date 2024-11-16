package com.online.service;

public interface UserService {
    boolean validateUser(String username, String password);
    void registerUser(String username, String password,String email,int phone,String address); // Add method for registration
    boolean isUsernameTaken(String username); // Add method to check if username is taken
}
