package com.online.service.impl;

import com.online.domain.TicketPool;
import com.online.repository.TicketPoolDao;
import com.online.resource.TicketPoolResourse;
import com.online.service.TicketPoolService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketPoolServiceImpl implements TicketPoolService {

    private final TicketPoolDao ticketPoolDao;
    private final ModelMapper modelMapper;

    @Autowired
    public TicketPoolServiceImpl(TicketPoolDao ticketPoolDao) {
        this.ticketPoolDao = ticketPoolDao;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public TicketPoolResourse addEvent(TicketPoolResourse ticketPoolResourse) {
        // Map TicketPoolResource to TicketPool
        TicketPool ticketPool = new TicketPool();
        ticketPool.setEventName(ticketPoolResourse.getEventName());
        ticketPool.setEventDescription(ticketPoolResourse.getEventDescription());
        ticketPool.setEventLocation(ticketPoolResourse.getEventLocation());
        ticketPool.setEventDate(ticketPoolResourse.getEventDate());
        ticketPool.setEventTime(ticketPoolResourse.getEventTime());
        ticketPool.setEventDuration(ticketPoolResourse.getEventDuration());
        ticketPool.setEventCategory(ticketPoolResourse.getEventCategory());
        ticketPool.setEventOrganizer(ticketPoolResourse.getEventOrganizer());
        ticketPool.setEventContact(ticketPoolResourse.getEventContact());
        ticketPool.setEventEmail(ticketPoolResourse.getEventEmail());

        // Set default values for other fields
        ticketPool.setStatusMessage("Scheduled");
        ticketPool.setCreatedBy("Admin");
        ticketPool.setCreatedDate(LocalDateTime.now().toString());

        // Save ticket pool details
        return modelMapper.map(ticketPoolDao.save(ticketPool), TicketPoolResourse.class);
    }

    @Override
    public TicketPool configureSystem(TicketPool ticketPool) {
        // Save ticket pool configuration
        return ticketPoolDao.save(ticketPool);
    }

    @Override
    public TicketPool getStatus() {
        // Fetch current status of the ticket pool
        return ticketPoolDao.findAll().get(0);
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
