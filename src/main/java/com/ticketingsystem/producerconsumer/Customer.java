package com.ticketingsystem.producerconsumer;

import com.ticketingsystem.model.TicketPool;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;
    private boolean running = true;

    public Customer(TicketPool ticketPool, int customerRetrievalRate) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000 / customerRetrievalRate);
                String ticket = ticketPool.retrieveTicket();
                if (ticket != null) {
                    System.out.println("Customer retrieved: " + ticket);
                } else {
                    System.out.println("Customer: No tickets available.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
