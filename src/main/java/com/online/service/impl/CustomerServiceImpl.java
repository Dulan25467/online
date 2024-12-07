package com.online.service.impl;

import com.online.domain.CustomerDetails;
import com.online.domain.TicketPool;
import com.online.repository.CustomerDao;
import com.online.repository.TicketPoolDao;
import com.online.resource.CustomerResourse;
import com.online.service.CustomerService;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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
    public boolean bookTickets(Long eventId, String username, int numberOfTickets) {
        // Fetch the event using eventId
        TicketPool ticketPool = ticketPoolDao.findById(eventId).orElse(null);
        if (ticketPool == null) {
            throw new IllegalArgumentException("Event with ID " + eventId + " not found");
        }

        // Check if enough tickets are available
        if (ticketPool.getAvailableTickets() < numberOfTickets) {
            return false;
        }

        // Update available tickets
        ticketPool.setAvailableTickets(ticketPool.getAvailableTickets() - numberOfTickets);
        ticketPoolDao.save(ticketPool);

        // Create a new customer
        CustomerDetails customer = customerDao.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setBookedtickets(customer.getBookedtickets() + ", " + eventId.getClass() + " (" + numberOfTickets + ")");
        customerDao.save(customer);

        return true;
    }


}