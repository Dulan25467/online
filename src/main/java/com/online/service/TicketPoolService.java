package com.online.service;

import com.online.domain.TicketPool;
import com.online.resource.TicketPoolResourse;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface TicketPoolService {

        TicketPoolResourse addEvent(TicketPoolResourse ticketPoolResourse);

        List<TicketPoolResourse> viewEvents();

        TicketPoolResourse updateEvent(TicketPoolResourse ticketPoolResourse);

        boolean deleteEvent(Long id); }
