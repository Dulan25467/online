package com.online.service.impl;

import com.online.domain.TicketPool;

import com.online.repository.TicketPoolDao;
import com.online.service.TicketPoolService;
import com.online.websocket.TicketPoolWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TicketPoolServiceImpl implements TicketPoolService {

    private final TicketPoolDao ticketPoolDao;
    private final TicketPoolWebSocketHandler webSocketHandler;

    @Autowired
    public TicketPoolServiceImpl(TicketPoolDao ticketPoolDao, TicketPoolWebSocketHandler webSocketHandler) {
        this.ticketPoolDao = ticketPoolDao;
        this.webSocketHandler = webSocketHandler;
    }

    @Override
    public TicketPool configureSystem(TicketPool ticketPool) {
        // Save ticket pool configuration
        TicketPool savedConfig = ticketPoolDao.save(ticketPool);
        // Notify WebSocket clients
        webSocketHandler.sendMessage("System configured with parameters: " + savedConfig.toString());
        return savedConfig;
    }

    @Override
    public TicketPool getStatus() {
        // Retrieve current status
        TicketPool status = ticketPoolDao.findFirstByOrderByIdDesc()
                .orElse(new TicketPool(0, 0, 0, 0, "No configuration found."));
        // Notify WebSocket clients
        webSocketHandler.sendMessage("Current system status: " + status.toString());
        return status;
    }

    @Override
    public void startOperations() {
        // Business logic to start operations
        webSocketHandler.sendMessage("Ticket operations started.");
    }

    @Override
    public void stopOperations() {
        // Business logic to stop operations
        webSocketHandler.sendMessage("Ticket operations stopped.");
    }
}