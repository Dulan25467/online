package com.ticketingsystem.producerconsumer;

import com.ticketingsystem.model.TicketPool;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int ticketReleaseRate;
    private final int totalTickets; // Maximum number of tickets to release
    private int ticketsReleased = 0; // Counter for released tickets
    private boolean running = true;

    public Vendor(TicketPool ticketPool, int ticketReleaseRate, int totalTickets) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.totalTickets = totalTickets;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000 / ticketReleaseRate);

                synchronized (ticketPool) {
                    if (ticketsReleased < totalTickets) {
                        String ticket = "Ticket-" + (ticketsReleased + 1);
                        if (ticketPool.addTicket(ticket)) {
                            ticketsReleased++;
                            System.out.println("Vendor added: " + ticket + " (Total released: " + ticketsReleased + ")");
                        } else {
                            System.out.println("Vendor: Ticket pool is full. Can't add tickets.");
                        }
                    }

                    // Stop releasing tickets if the limit is reached
                    if (ticketsReleased >= totalTickets) {
                        System.out.println("Vendor: All " + totalTickets + " tickets have been released.");
                        stop();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
