package com.ticketingsystem.model;

import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private Queue<String> tickets;
    private int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.tickets = new LinkedList<>();
    }

    public boolean addTicket(String ticket) {
        if (tickets.size() < maxCapacity) {
            tickets.add(ticket);
            return true;
        }
        return false; // Pool is full
    }

    public String retrieveTicket() {
        return tickets.poll(); // Retrieve the first ticket from the queue
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    // Add method to change the max capacity if needed
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}
