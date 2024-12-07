package com.online.controller;
import com.online.exeption.ApiResponse;
import com.online.resource.CustomerResourse;
import com.online.service.CustomerService;
import com.online.service.TicketPoolService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final TicketPoolService ticketPoolService;
    private ModelMapper modelMapper;

    @Autowired
    public CustomerController(CustomerService customerService, TicketPoolService ticketPoolService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
        this.ticketPoolService = ticketPoolService;
    }

    @PostMapping("/bookTickets/{eventId}/{username}")
    public ResponseEntity<ApiResponse> bookTickets(
            @PathVariable Long eventId,
            @PathVariable String username,
            @RequestParam int numberOfTickets) {
        try {
            boolean success = customerService.bookTickets(eventId, username, numberOfTickets);
            if (success) {
                return ResponseEntity.ok(new ApiResponse("Tickets booked successfully", true, null));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse("Failed to book tickets. Not enough available tickets.", false, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("An error occurred while booking tickets", false, null));
        }
    }



}
