package com.ticketingsystem.producerconsumer;

import com.ticketingsystem.model.TicketPool;

public class TicketManager {
    private TicketPool ticketPool;
    private int maxTicketCapacity;
    private int totalTicketsAvailable;
    private int ticketReleaseRate;
    private int customerRetrievalRate;

    private boolean isRunning;

    private int vendorReleasedTickets;
    private int customerBoughtTickets;

    private Thread vendorThread;
    private Thread customerThread;

    public TicketManager(int maxTicketCapacity, int totalTicketsAvailable, int ticketReleaseRate, int customerRetrievalRate) {
        this.maxTicketCapacity = maxTicketCapacity;
        this.totalTicketsAvailable = totalTicketsAvailable;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;

        this.ticketPool = new TicketPool(maxTicketCapacity);
        this.vendorReleasedTickets = 0;
        this.customerBoughtTickets = 0;
        this.isRunning = false; // Default to not running
    }

    public void startOperations() {
        if (isRunning) {
            System.out.println("Ticket system is already running.");
            return; // Exit if already running
        }

        isRunning = true;

        // Vendor thread for releasing tickets
        vendorThread = new Thread(() -> {
            while (isRunning) {
                releaseTickets(ticketReleaseRate); // Release tickets at a set rate
                try {
                    Thread.sleep(1000); // Sleep for 1 second to control release rate
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Customer thread for buying tickets
        customerThread = new Thread(() -> {
            while (isRunning) {
                buyTickets(customerRetrievalRate); // Customers buy tickets at a set rate
                try {
                    Thread.sleep(1000); // Sleep for 1 second to control retrieval rate
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Start the threads
        vendorThread.start();
        customerThread.start();
        System.out.println("Ticket system started.");
    }

    public void stopOperations() {
        if (!isRunning) {
            System.out.println("Ticket system is already stopped.");
            return; // Exit if already stopped
        }

        isRunning = false;

        // Interrupt the threads to stop the operations
        if (vendorThread != null) {
            vendorThread.interrupt();
        }
        if (customerThread != null) {
            customerThread.interrupt();
        }
        System.out.println("Ticket system stopped.");
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void releaseTickets(int numberOfTickets) {
        for (int i = 0; i < numberOfTickets; i++) {
            if (vendorReleasedTickets >= totalTicketsAvailable) {
                System.out.println("Vendor cannot add more tickets. Total tickets available limit reached.");
                break; // Stop releasing tickets once the limit is reached
            }

            // Add ticket to the pool
            if (ticketPool.addTicket("Ticket-" + (vendorReleasedTickets + 1))) {
                vendorReleasedTickets++;
                System.out.println("Vendor added: Ticket-" + vendorReleasedTickets
                        + " (Total released: " + vendorReleasedTickets + ")");
            } else {
                System.out.println("Ticket pool is full. Cannot add more tickets.");
                break;
            }
        }
        checkAndStopSystem(); // Check if the system should stop
    }

    public void buyTickets(int numberOfTickets) {
        for (int i = 0; i < numberOfTickets; i++) {
            String ticket = ticketPool.retrieveTicket();
            if (ticket != null) {
                customerBoughtTickets++;
                System.out.println("Customer retrieved: " + ticket);
            } else {
                System.out.println("No tickets available for retrieval.");
                break;
            }
        }
        checkAndStopSystem(); // Check if the system should stop
    }

    private void checkAndStopSystem() {
        if (vendorReleasedTickets >= totalTicketsAvailable
                && customerBoughtTickets >= totalTicketsAvailable) {
            System.out.println("All tickets have been released and sold. Stopping the system...");
            stopOperations();
        }
    }

    public int getVendorReleasedTickets() {
        return vendorReleasedTickets;
    }

    public int getCustomerBoughtTickets() {
        return customerBoughtTickets;
    }

    public int getAvailableTickets() {
        return ticketPool.getMaxCapacity() - vendorReleasedTickets;
    }

    public int getCurrentlyAvailableTickets() {
        return ticketPool.getMaxCapacity() - vendorReleasedTickets + customerBoughtTickets;
    }

    public void updateConfiguration(int maxTicketCapacity, int totalTicketsAvailable, int ticketReleaseRate, int customerRetrievalRate) {
        this.maxTicketCapacity = maxTicketCapacity;
        this.totalTicketsAvailable = totalTicketsAvailable;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;

        // Update the TicketPool to reflect the new max capacity
        this.ticketPool.setMaxCapacity(maxTicketCapacity);
        this.vendorReleasedTickets = 0; // Reset the released tickets count
        this.customerBoughtTickets = 0; // Reset the bought tickets count
        System.out.println("Configuration updated.");
    }
}
