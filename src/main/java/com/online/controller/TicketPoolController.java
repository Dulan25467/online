package com.online.controller;

import com.online.domain.TicketPool;
import com.online.service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticketingSystem")
public class TicketPoolController {

    private final TicketPoolService ticketPoolService;

    @Autowired
    public TicketPoolController(TicketPoolService ticketPoolService) {
        this.ticketPoolService = ticketPoolService;
    }

    // Endpoint to configure the system with ticket pool details
    @PostMapping("/configure")
    public ResponseEntity<TicketPool> configureSystem(@RequestBody TicketPool ticketPool) {
        try {
            TicketPool savedConfig = ticketPoolService.configureSystem(ticketPool);
            return ResponseEntity.ok(savedConfig);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Endpoint to fetch current status of the ticket pool
    @GetMapping("/status")
    public ResponseEntity<TicketPool> getTicketPoolStatus() {
        try {
            TicketPool status = ticketPoolService.getStatus();
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Endpoint to start ticket pool operations
    @PostMapping("/start")
    public ResponseEntity<String> startTicketOperations() {
        try {
            ticketPoolService.startOperations();
            return ResponseEntity.ok("Ticket operations started successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to start ticket operations.");
        }
    }

    // Endpoint to stop ticket pool operations
    @PostMapping("/stop")
    public ResponseEntity<String> stopTicketOperations() {
        try {
            ticketPoolService.stopOperations();
            return ResponseEntity.ok("Ticket operations stopped successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to stop ticket operations.");
        }
    }

    // Optional endpoint to fetch the full status
    @GetMapping("/full-status")
    public ResponseEntity<String> getFullStatus() {
        try {
            // Here, you could return a more detailed status if necessary
            return ResponseEntity.ok("Full status: Operations are running.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to fetch full status.");
        }
    }
}