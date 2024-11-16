package com.online.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vendor_detail")

public class VendorDetail {
    @Id
    private int id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String status;
    private String createdBy;
    private String createdDate;
    private String updatedBy;
    private String updatedDate;
    private String deletedBy;
    private String deletedDate;



}
