package com.online.service;

import com.online.resource.TicketPoolResourse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface TicketPoolService {
        TicketPoolResourse addEvent(TicketPoolResourse ticketPoolResourse, Long vendorId, String username);
        List<TicketPoolResourse> viewEvents();
        TicketPoolResourse updateEvent(TicketPoolResourse ticketPoolResourse, Long vendorId, String username);
        boolean deleteEvent(Long id);
        TicketPoolResourse viewEvent(Long id);
        TicketPoolResourse bookTickets(Long eventId, Long customerId, List<Integer> ticketNumbers);
        List<Integer> getBookedTickets(Long eventId);

}
