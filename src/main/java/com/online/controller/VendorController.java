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


    @PostMapping("/addVender")
    public VenderResourse addVender(@RequestBody VenderResourse venderResourse){
        return vendorService.addVender(venderResourse);
    }

    @PutMapping("/updateVender")
    public VenderResourse updateVender(@RequestBody VenderResourse venderResourse){
        return vendorService.updateVender(venderResourse);
    }

    @DeleteMapping("/deleteVender/{id}")
    public void deleteVender(@PathVariable int id){
        vendorService.deleteVender(id);
    }

    @GetMapping("/getVender/{id}")
    public VenderResourse getVender(@PathVariable int id){
        return vendorService.getVender(id);
    }

}
