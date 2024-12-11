package com.online.controller;

import com.online.resource.VenderResourse;
import com.online.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/vendor")
public class VendorController {
    @Autowired
    private VendorService vendorService;

    //vender add the tickes for the buy check ticket_pool_details table max_ticket_capacity and vendor get to buy tickets
    //max_ticket_capacity >= total_tickets


}
