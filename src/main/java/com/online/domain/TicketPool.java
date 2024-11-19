package com.online.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ticket_pool") // Optional if the table name is different
public class TicketPool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;
    private String statusMessage;

    public TicketPool(int i, int i1, int i2, int i3, String s) {

    }


    @Override
    public String toString() {
        return "TicketPool{" +
                "totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", maxTicketCapacity=" + maxTicketCapacity +
                ", statusMessage='" + statusMessage + '\'' +
                '}';
    }
}
