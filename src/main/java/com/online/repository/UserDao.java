package com.online.repository;

import com.online.domain.UserDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<UserDomain, Long> {
    Optional<UserDomain> findByUsername(String username);
    Optional<UserDomain> findByEmail(String email);}
