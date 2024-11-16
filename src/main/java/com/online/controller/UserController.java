package com.online.controller;

import com.online.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam int phone, @RequestParam String address) {
        userService.registerUser(username, password, email, phone, address);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        boolean isValid = userService.validateUser(username, password);
        return isValid ? "Login successful!" : "Invalid credentials.";
    }
}
