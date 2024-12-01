package com.online.service;

import com.online.domain.TicketPool;
import com.online.resource.TicketPoolResourse;
import org.springframework.stereotype.Service;


@Service
public interface TicketPoolService {
        TicketPool configureSystem(TicketPool ticketPool);

        TicketPool getStatus();

        void startOperations();

        void stopOperations();

        TicketPoolResourse addEvent(TicketPoolResourse ticketPoolResourse);


}
