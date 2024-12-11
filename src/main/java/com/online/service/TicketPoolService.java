package com.online.service;

import com.online.resource.TicketPoolResourse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface TicketPoolService {
        List<TicketPoolResourse> viewEvents();
        boolean deleteEvent(Long id);
        TicketPoolResourse viewEvent(Long id);
        TicketPoolResourse bookTickets(Long eventId, Long customerId, List<Integer> ticketNumbers);
        List<Integer> getBookedTickets(Long eventId);
        TicketPoolResourse createEvent(TicketPoolResourse ticketPoolResourse);
        TicketPoolResourse modifyEvent(TicketPoolResourse ticketPoolResourse, Long eventId);

        TicketPoolResourse addVendorTickets(Long eventId, Long vendorId, int ticketsToAdd);
        List<TicketPoolResourse> getVendorTicketHistory(Long vendorId);
}
