package com.online.service.impl;

import com.online.domain.CustomerDetails;
import com.online.repository.CustomerDao;
import com.online.resource.CustomerResourse;
import com.online.service.CustomerService;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Data
@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao) {  // Updated to CustomerDao
        this.customerDao = customerDao;
        this.modelMapper = new ModelMapper();
    }
}