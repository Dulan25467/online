package com.online.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketPoolResourse {

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
}
