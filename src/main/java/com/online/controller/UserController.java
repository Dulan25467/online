package com.online.controller;

import com.online.resource.UserResourse;
import com.online.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserResourse userResource, BindingResult result) {
        if (result.hasErrors()) {
            // Log validation errors
            result.getFieldErrors().forEach(error ->
                    System.out.println("Validation error: " + error.getField() + " - " + error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(result.getFieldErrors());
        }

        try {
            UserResourse registeredUser = userService.registerUser(userResource);
            return ResponseEntity.ok("User registered successfully: " + registeredUser);
        } catch (Exception e) {
            // Log any service or database-related exceptions
            System.out.println("Error during registration: " + e.getMessage());
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }




    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserResourse loginRequest) {
        UserResourse userResource = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(userResource);
    }

}
