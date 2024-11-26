package com.online.service.impl;

import com.online.domain.UserDomain;
import com.online.repository.UserDao;
import com.online.resource.UserResourse;
import com.online.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;



    @Override
    public UserResourse registerUser(UserResourse userResourse) {
        if (userDao.findByUsername(userResourse.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userDao.findByEmail(userResourse.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        UserDomain userDomain = mapToDomain(userResourse);
        try {
            UserDomain savedUser = userDao.save(userDomain);
            return mapToResource(savedUser);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the user: " + e.getMessage());
        }
    }


    @Override
    public UserResourse login(String username, String password) {
        UserDomain userDomain = userDao.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid Username or password"));

        // Check password directly without encoding
        if (!password.equals(userDomain.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return mapToResource(userDomain);
    }

    private UserDomain mapToDomain(UserResourse UserResourse) {
        return new UserDomain(
                null,
                UserResourse.getUsername(),
                UserResourse.getPassword(),
                UserResourse.getEmail(),
                UserResourse.getPhone(),
                UserResourse.getAddress()
        );
    }

    private UserResourse mapToResource(UserDomain userDomain) {
        return new UserResourse(
                userDomain.getUsername(),
                userDomain.getPassword(),
                userDomain.getEmail(),
                userDomain.getPhone(),
                userDomain.getAddress()
        );
    }
}
