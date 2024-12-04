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

}
