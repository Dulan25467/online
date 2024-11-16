package com.online.repository;

import com.online.domain.User; // Ensure this imports your custom User class
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
