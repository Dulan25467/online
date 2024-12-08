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


}
