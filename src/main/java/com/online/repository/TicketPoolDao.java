package com.online.repository;

import com.online.domain.TicketPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketPoolDao extends JpaRepository<TicketPool, Long> {
    @Override
    List<TicketPool> findAll();

}
