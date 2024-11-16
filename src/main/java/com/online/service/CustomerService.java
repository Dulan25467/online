package com.online.service;

import com.online.resourse.CustomerResourse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    List<CustomerResourse> getAllCustomers();
    CustomerResourse getCustomer(int id);
    CustomerResourse addCustomer(CustomerResourse customerResourse);
    CustomerResourse updateCustomer(CustomerResourse customerResourse);
    void deleteCustomer(int id);
}
