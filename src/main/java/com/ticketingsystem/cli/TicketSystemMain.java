package com.ticketingsystem.cli;

import com.ticketingsystem.config.TicketSystemConfig;
import com.ticketingsystem.producerconsumer.TicketManager;

import java.util.Scanner;

public class TicketSystemMain {

    public static void main(String[] args) {
        System.out.println("Welcome to the Ticketing System CLI");

        // Initialize configuration
        TicketSystemConfig config = new TicketSystemConfig();

        // Initialize the ticket manager
        TicketManager ticketManager = new TicketManager(config);

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
                    configureTicketSystem(config);
                    ticketManager = new TicketManager(config); // Reinitialize the manager after configuration
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

    // Method to handle configuration of ticket system
    private static void configureTicketSystem(TicketSystemConfig config) {
        System.out.println("Configuring Ticket System...");

        // Setup configuration
        config.setupConfiguration();
    }
}
