package com.online.service.impl;

import com.online.domain.CustomerDetails;
import com.online.repository.CustomerDao;
import com.online.resourse.CustomerResourse;
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

    @Override
    public List<CustomerResourse> getAllCustomers() {
        List<CustomerDetails> customerList = customerDao.findAll();
        return modelMapper.map(customerList, new TypeToken<List<CustomerResourse>>(){}.getType());
    }

    @Override
    public CustomerResourse getCustomer(int id) {
        return modelMapper.map(customerDao.findById(id), CustomerResourse.class);
    }

    @Override
    public CustomerResourse addCustomer(CustomerResourse customerResourse) {
        customerDao.save(modelMapper.map(customerResourse, CustomerDetails.class));
        return customerResourse;
    }

    @Override
    public CustomerResourse updateCustomer(CustomerResourse customerResourse) {
        customerDao.save(modelMapper.map(customerResourse, CustomerDetails.class));
        return customerResourse;
    }

    @Override
    public void deleteCustomer(int id) {
        customerDao.deleteById(id);
    }
}
