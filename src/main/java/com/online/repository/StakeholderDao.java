package com.online.repository;

import com.online.domain.StakeholderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StakeholderDao extends JpaRepository<StakeholderDetails, Integer> {
    StakeholderDetails findByUsername(String username);
}
