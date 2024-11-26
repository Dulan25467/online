package com.online.repository;

import com.online.domain.CustomerDetails;
import com.online.resource.CustomerResourse;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CustomerDaoImpl {
    private final CustomerDao customerDao;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerDaoImpl(@Lazy CustomerDao customerDao, ModelMapper modelMapper) {
        this.customerDao = customerDao;
        this.modelMapper = modelMapper;
    }

    public List<CustomerResourse> getAllCustomers() {
        List<CustomerDetails> customerList = customerDao.findAll();
        return modelMapper.map(customerList, new TypeToken<List<CustomerResourse>>(){}.getType());
    }

    public CustomerResourse getCustomer(int id) {
        return modelMapper.map(customerDao.findById(id), CustomerResourse.class);
    }

    public CustomerResourse addCustomer(CustomerResourse customerResourse) {
        customerDao.save(modelMapper.map(customerResourse, CustomerDetails.class));
        return customerResourse;
    }

    public CustomerResourse updateCustomer(CustomerResourse customerResourse) {
        customerDao.save(modelMapper.map(customerResourse, CustomerDetails.class));
        return customerResourse;
    }

    public void deleteCustomer(int id) {
        customerDao.deleteById(id);
    }

}
