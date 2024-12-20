package com.online.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer_details")
public class CustomerDetails {
    @Id
    private long id;
    private String username;
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
    private int bookedTickets;

    @ManyToMany(mappedBy = "customers")
    private List<TicketPool> events;

    public void setBookedTickets(int bookedTickets) {
        this.bookedTickets = bookedTickets;
    }
}