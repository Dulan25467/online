package com.online.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketPoolResourse {
    private Long id;
    private String eventName;
    private String eventDescription;
    private String eventLocation;
    private String eventDate;
    private String eventTime;
    private String eventCategory;
    private String eventContact;
    private String totalTickets;

}
