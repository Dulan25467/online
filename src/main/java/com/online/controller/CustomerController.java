package com.online.controller;
import com.online.resourse.CustomerResourse;
import com.online.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/customer")
public class CustomerController {

    private final CustomerService customerService;
    private ModelMapper modelMapper;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/getCustomers")
    public List<CustomerResourse> getCustomers() {
        return customerService.getAllCustomers();
    }
   @GetMapping("/getCustomer/{id}")
    public CustomerResourse getCustomer(@PathVariable int id){
        return customerService.getCustomer(id);
    }
    @PostMapping("/addCustomer")
    public CustomerResourse addCustomer(@RequestBody CustomerResourse customerResourse){
        return customerService.addCustomer(customerResourse);
    }
    @PutMapping("/updateCustomer")
    public CustomerResourse updateCustomer(@RequestBody CustomerResourse customerResourse){
        return customerService.updateCustomer(customerResourse);
    }
    @DeleteMapping("/deleteCustomer/{id}")
    public void deleteCustomer(@PathVariable int id){
        customerService.deleteCustomer(id);
    }

}
