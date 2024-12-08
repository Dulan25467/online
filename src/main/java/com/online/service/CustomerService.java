package com.online.service;

import com.online.domain.CustomerDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CustomerService {
    boolean bookTicket(Long customerId, Long eventId, String ticketNumbers);

}
