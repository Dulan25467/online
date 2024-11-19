package com.ticketingsystem.config;

import java.util.Scanner;

public class TicketSystemConfig {

    private int maxTicketCapacity;
    private int totalTicketsAvailable;
    private int ticketReleaseRate;
    private int customerRetrievalRate;

    public void setupConfiguration() {
        Scanner scanner = new Scanner(System.in);

        // Get configuration values from the user
        System.out.print("Enter max ticket capacity: ");
        maxTicketCapacity = scanner.nextInt();

        System.out.print("Enter total tickets available: ");
        totalTicketsAvailable = scanner.nextInt();

        System.out.print("Enter ticket release rate (per second): ");
        ticketReleaseRate = scanner.nextInt();

        System.out.print("Enter customer retrieval rate (per second): ");
        customerRetrievalRate = scanner.nextInt();

        System.out.println("Configuration complete!");
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }
    public int getTotalTicketsAvailable() {
        return totalTicketsAvailable;
    }
}
