package com.online.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ticket_event_pool") // Optional if the table name is different
public class TicketPool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String eventName;
    private String eventDescription;
    private String eventLocation;
    private String eventDate;
    private String eventTime;
    private String eventDuration;
    private String eventCategory;
    private String eventOrganizer;
    private String eventContact;
    private String eventEmail;
    private int vendor_get_tickets;
    private double ticketPrice;

    private int availableTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;
    private String statusMessage;

    private String createdBy;
    private String createdDate;
    private String updatedBy;
    private String updatedDate;
    private String deletedBy;
    private String deletedDate;

    @ElementCollection
    private List<Integer> bookedTickets = new ArrayList<>();



    @ManyToOne
    @JoinColumn(name = "organizer_id") // Foreign key
    private StakeholderDetails organizer;

    @ManyToMany
    @JoinTable(
            name = "event_vendors",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "vendor_id")
    )
    private List<VendorDetail> vendors;

    @ManyToMany
    @JoinTable(
            name = "event_customers",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private List<CustomerDetails> customers;

}
