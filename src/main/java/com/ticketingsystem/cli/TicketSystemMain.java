package com.ticketingsystem.cli;

import com.ticketingsystem.producerconsumer.TicketManager;
import java.util.Scanner;

public class TicketSystemMain {

    public static void main(String[] args) {
        System.out.println("Welcome to the Ticketing System CLI");

        // Default values for configuration
        int maxTicketCapacity = 100;    // Max ticket capacity
        int totalTicketsAvailable = 50; // Total tickets available
        int ticketReleaseRate = 5;      // Ticket release rate
        int customerRetrievalRate = 3;  // Customer retrieval rate

        // Initialize the ticket manager with configuration values
        TicketManager ticketManager = new TicketManager(maxTicketCapacity, totalTicketsAvailable, ticketReleaseRate, customerRetrievalRate);

        // Command handling
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Configure Ticket System");
            System.out.println("2. Start Ticket System");
            System.out.println("3. Stop Ticket System");
            System.out.println("4. Check Ticket System Status");
            System.out.println("5. Exit");
            System.out.print("Enter command: ");
            String command = scanner.nextLine().trim().toUpperCase();

            switch (command) {
                case "1":
                    // Call to configure ticket system
                    configureTicketSystem(ticketManager);
                    break;

                case "2":
                    // Start Ticket System
                    ticketManager.startOperations();
                    break;

                case "3":
                    // Stop Ticket System
                    ticketManager.stopOperations();
                    break;

                case "4":
                    // Check Ticket System Status
                    if (ticketManager.isRunning()) {
                        System.out.println("Ticket system is currently running.");
                    } else {
                        System.out.println("Ticket system is currently stopped.");
                    }

                    System.out.println("Vendor released tickets: " + ticketManager.getVendorReleasedTickets());
                    System.out.println("Customer bought tickets: " + ticketManager.getCustomerBoughtTickets());
                    System.out.println("System available tickets: " + ticketManager.getAvailableTickets());
                    System.out.println("Currently available tickets: " + ticketManager.getCurrentlyAvailableTickets());
                    break;

                case "5":
                    // Exit
                    if (ticketManager.isRunning()) {
                        ticketManager.stopOperations(); // Ensure operations are stopped before exiting
                    }
                    running = false;
                    System.out.println("Exiting the system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }

        scanner.close();
    }

    private static void configureTicketSystem(TicketManager ticketManager) {
        System.out.println("Configuring Ticket System...");

        // Allow user to modify configuration, for example:
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Max Ticket Capacity: ");
        int maxTicketCapacity = scanner.nextInt();

        System.out.print("Enter Total Tickets Available: ");
        int totalTicketsAvailable = scanner.nextInt();

        System.out.print("Enter Ticket Release Rate: ");
        int ticketReleaseRate = scanner.nextInt();

        System.out.print("Enter Customer Retrieval Rate: ");
        int customerRetrievalRate = scanner.nextInt();

        // Update TicketManager with new configuration
        ticketManager.updateConfiguration(maxTicketCapacity, totalTicketsAvailable, ticketReleaseRate, customerRetrievalRate);

        System.out.println("Ticket System configuration updated.");
    }
}
