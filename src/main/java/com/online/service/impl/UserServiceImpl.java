package com.online.service.impl;

import com.online.domain.User;
import com.online.repository.UserDao;
import com.online.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Data
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean validateUser(String username, String password) {
        return userDao.findByUsername(username)
                .map(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(false); // Return false if user not found
    }

    @Override
    public void registerUser(String username, String password, String email, int phone, String address) {
        // Username validation: only letters
        if (!username.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Username should contain only letters. No numbers or special characters allowed.");
        }

        // Username uniqueness check
        if (userDao.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists. Please choose a different username.");
        }

        // Email validation with regex
        if (!email.matches("^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,3}$")) {
            throw new IllegalArgumentException("Invalid email format. Please provide a valid email address.");
        }

        // Phone validation: 8 to 12 digits
        String phoneString = String.valueOf(phone);
        if (!phoneString.matches("\\d{8,12}")) {
            throw new IllegalArgumentException("Phone number must be between 8 to 12 digits and contain only numbers.");
        }

        // Password hashing
        String hashedPassword = passwordEncoder.encode(password);

        // Create and save the user
        User user = new User(username, hashedPassword, email, phone, address);
        userDao.saveAndFlush(user); // Save the user in the database
    }

    @Override
    public boolean isUsernameTaken(String username) {
        return userDao.findByUsername(username).isPresent();
    }
}
