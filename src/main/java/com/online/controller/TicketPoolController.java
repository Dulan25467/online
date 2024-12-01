package com.online.controller;

import com.online.domain.TicketPool;
import com.online.exeption.ApiResponse;
import com.online.exeption.CommonExeption;
import com.online.resource.TicketPoolResourse;
import com.online.service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/ticketingSystem")
public class TicketPoolController {

    private final TicketPoolService ticketPoolService;

    @Autowired
    public TicketPoolController(TicketPoolService ticketPoolService) {
        this.ticketPoolService = ticketPoolService;
    }

    @PostMapping("/addEvent")
    public ResponseEntity<ApiResponse> addEvent(@RequestBody TicketPoolResourse ticketPoolResourse) {
        CommonExeption exception = new CommonExeption();

        // Validate the ticket pool details
        if (ticketPoolResourse.getEventName() == null || ticketPoolResourse.getEventName().isEmpty()) {
            exception.addError("eventName", "Event name is required");
        }
        if (ticketPoolResourse.getEventLocation() == null || ticketPoolResourse.getEventLocation().isEmpty()) {
            exception.addError("eventLocation", "Event location is required");
        }
        if (!exception.getErrors().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Invalid ticket pool details", false, exception.getErrors()));
        }

        // Save the ticket pool details
        TicketPoolResourse savedTicketPool = ticketPoolService.addEvent(ticketPoolResourse);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Event added successfully", true, savedTicketPool));
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