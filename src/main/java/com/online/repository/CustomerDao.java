package com.online.repository;

import com.online.domain.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDao extends JpaRepository<CustomerDetails,Long> {
    @Override
    List<CustomerDetails> findAll();
}
