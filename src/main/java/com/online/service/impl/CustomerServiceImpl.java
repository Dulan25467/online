package com.online.service.impl;

import com.online.domain.CustomerDetails;
import com.online.domain.TicketPool;
import com.online.repository.CustomerDao;
import com.online.repository.TicketPoolDao;
import com.online.service.CustomerService;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Data
@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;
    private final TicketPoolDao ticketPoolDao;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao,TicketPoolDao ticketPoolDao,ModelMapper modelMapper) {  // Updated to CustomerDao
        this.customerDao = customerDao;
        this.ticketPoolDao = ticketPoolDao;
        this.modelMapper = new ModelMapper();
    }
    @Override
    public boolean bookTicket(Long customerId, Long eventId, String ticketNumbers) {
        // Fetch customer
        Optional<CustomerDetails> optionalCustomer = customerDao.findById(customerId);
        if (!optionalCustomer.isPresent()) {
            throw new IllegalArgumentException("Customer not found");
        }
        CustomerDetails customer = optionalCustomer.get();

        // Fetch event
        Optional<TicketPool> optionalEvent = ticketPoolDao.findById(eventId);
        if (!optionalEvent.isPresent()) {
            throw new IllegalArgumentException("Event not found");
        }
        TicketPool event = optionalEvent.get();

        // Fetch current booked tickets for the customer
        String bookedTickets = customer.getBookedtickets();
        if (bookedTickets == null) {
            bookedTickets = "";
        }

        // Check if the requested tickets are available and not already booked by the customer
        String[] requestedTickets = ticketNumbers.split(",");
        for (String ticket : requestedTickets) {
            if (bookedTickets.contains(ticket)) {
                throw new IllegalArgumentException("Ticket " + ticket + " is already booked.");
            }
        }

        // Add the booked tickets to the customer's record
        bookedTickets += (bookedTickets.isEmpty() ? "" : ",") + ticketNumbers;
        customer.setBookedtickets(bookedTickets);

        // Update the customer's ticket information
        customerDao.save(customer);

        return true;
    }
}
