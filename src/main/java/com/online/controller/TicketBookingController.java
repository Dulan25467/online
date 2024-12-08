package com.online.controller;

import com.online.service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/tickets")
public class TicketBookingController {

    private final CustomerServiceImpl customerService;

    @Autowired
    public TicketBookingController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/book")
    public boolean bookTicket(@RequestParam Long customerId,
                              @RequestParam Long eventId,
                              @RequestParam String ticketNumbers) {
        return customerService.bookTicket(customerId, eventId, ticketNumbers);
    }
}
