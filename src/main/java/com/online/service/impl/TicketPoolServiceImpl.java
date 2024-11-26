package com.online.service.impl;

import com.online.domain.TicketPool;
import com.online.repository.TicketPoolDao;
import com.online.service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketPoolServiceImpl implements TicketPoolService {

    private final TicketPoolDao ticketPoolDao;

    @Autowired
    public TicketPoolServiceImpl(TicketPoolDao ticketPoolDao) {
        this.ticketPoolDao = ticketPoolDao;
    }

    @Override
    public TicketPool configureSystem(TicketPool ticketPool) {
        // Save ticket pool configuration
        return ticketPoolDao.save(ticketPool);
    }

    @Override
    public TicketPool getStatus() {
        // Retrieve current status
        return ticketPoolDao.findFirstByOrderByIdDesc()
                .orElse(new TicketPool(0, 0, 0, 0, "No configuration found."));
    }

    @Override
    public void startOperations() {
        // Business logic to start operations
        System.out.println("Ticket operations started.");
    }

    @Override
    public void stopOperations() {
        // Business logic to stop operations
        System.out.println("Ticket operations stopped.");
    }
}
