package com.online.controller;


import com.online.exeption.ApiResponse;
import com.online.exeption.CommonExeption;
import com.online.resource.TicketPoolResourse;
import com.online.service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
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



    @GetMapping("/viewEvents")
    public ResponseEntity<ApiResponse> viewEvents() {
        try {
            // Fetch all events
            List<TicketPoolResourse> events = ticketPoolService.viewEvents();
            return ResponseEntity.ok(new ApiResponse("Events fetched successfully", true, events));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Failed to fetch events", false, null));
        }
    }

    @PutMapping("/updateEvent")
    public ResponseEntity<ApiResponse> updateEvent(@RequestBody TicketPoolResourse ticketPoolResourse) {
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
        TicketPoolResourse savedTicketPool = ticketPoolService.updateEvent(ticketPoolResourse);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Event updated successfully", true, savedTicketPool));
    }

    @DeleteMapping("/deleteEvent/{id}")
    public ResponseEntity<ApiResponse> deleteEvent(@PathVariable Long id) {
        // Call the service layer to delete the event by ID
        boolean isDeleted = ticketPoolService.deleteEvent(id);

        if (isDeleted) {
            return ResponseEntity.ok(new ApiResponse("Event deleted successfully", true, null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Event not found", false, null));
        }
    }



}