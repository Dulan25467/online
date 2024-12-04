package com.online.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vendor_detail")

public class VendorDetail {
    @Id
    private long id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String createdBy;
    private String createdDate;
    private String updatedBy;
    private String updatedDate;
    private String deletedBy;
    private String deletedDate;

    private String eventname;
    private String eventDescription;
    private String eventLocation;
    private String eventDate;
    private String eventTime;
    private String eventCategory;
    private String eventContact;
    private int totalTickets;
    private int availableTickets;

    @ManyToMany(mappedBy = "vendors")
    private List<TicketPool> events;

}
