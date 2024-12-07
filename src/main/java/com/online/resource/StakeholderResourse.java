package com.online.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  StakeholderResourse {
    private int id;
    private String username;
    private String password;
    private String email;
    private String address;
    private String phone;
    private String stakeholderType; //Vender or customer
    private int vendorId; // Optional, applicable for Vendor
    private int customerId;
}
