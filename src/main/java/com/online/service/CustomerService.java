package com.online.service;

import com.online.resource.CustomerResourse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    boolean bookTickets(Long eventId, String username, int numberOfTickets);

}
