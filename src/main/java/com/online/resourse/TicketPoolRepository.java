package com.online.resourse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketPoolRepository {

    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;
    private String statusMessage;

//    @Override
//    public String toString() {
//        return "TicketPoolRepository{" +
//                "totalTickets=" + totalTickets +
//                ", ticketReleaseRate=" + ticketReleaseRate +
//                ", customerRetrievalRate=" + customerRetrievalRate +
//                ", maxTicketCapacity=" + maxTicketCapacity +
//                ", statusMessage='" + statusMessage + '\'' +
//                '}';
//    }

}
