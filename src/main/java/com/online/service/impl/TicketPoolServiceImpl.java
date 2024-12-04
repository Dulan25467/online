package com.online.service.impl;

import com.online.domain.TicketPool;
import com.online.exeption.ApiResponse;
import com.online.repository.TicketPoolDao;
import com.online.resource.TicketPoolResourse;
import com.online.service.TicketPoolService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        ticketPool.setEventCategory(ticketPoolResourse.getEventCategory());
        ticketPool.setEventContact(ticketPoolResourse.getEventContact());
        ticketPool.setEventEmail(ticketPoolResourse.getTotalTickets());
        // Set default values for other fields
        ticketPool.setStatusMessage("Scheduled");
        ticketPool.setCreatedBy("Admin");
        ticketPool.setCreatedDate(LocalDateTime.now().toString());

        // Save ticket pool details
        return modelMapper.map(ticketPoolDao.save(ticketPool), TicketPoolResourse.class);
    }

    @Override
    public List<TicketPoolResourse> viewEvents() {
        List<TicketPool> events = ticketPoolDao.findAll();
        return events.stream()
                .map(event -> modelMapper.map(event, TicketPoolResourse.class))
                .collect(Collectors.toList());
    }

    @Override
    public TicketPoolResourse updateEvent(TicketPoolResourse ticketPoolResourse) {
        // Map TicketPoolResource to TicketPool
        TicketPool ticketPool = new TicketPool();
        ticketPool.setEventName(ticketPoolResourse.getEventName());
        ticketPool.setEventDescription(ticketPoolResourse.getEventDescription());
        ticketPool.setEventLocation(ticketPoolResourse.getEventLocation());
        ticketPool.setEventDate(ticketPoolResourse.getEventDate());
        ticketPool.setEventTime(ticketPoolResourse.getEventTime());
        ticketPool.setEventCategory(ticketPoolResourse.getEventCategory());
        ticketPool.setEventContact(ticketPoolResourse.getEventContact());
        ticketPool.setEventEmail(ticketPoolResourse.getTotalTickets());

        // Set default values for other fields
        ticketPool.setStatusMessage("Scheduled");
        ticketPool.setCreatedBy("Admin");
        ticketPool.setCreatedDate(LocalDateTime.now().toString());

        // Save ticket pool details
        return modelMapper.map(ticketPoolDao.save(ticketPool), TicketPoolResourse.class);
    }

    @Override
    public boolean deleteEvent(Long id) {
        Optional<TicketPool> ticketPool = ticketPoolDao.findById(id);
        if (ticketPool.isPresent()) {
            ticketPoolDao.delete(ticketPool.get());
            return true;
        }
        return false;
    }




}
