package com.online.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StakeholderResourse {
    private String username;
    private String password;
    private String email;
    private String address;
    private String phone;
    private int id; // Add other fields if necessary
}
