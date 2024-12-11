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
            System.out.println("2. Configure VIP Customers");
            System.out.println("3. Start Ticket System");
            System.out.println("4. Stop Ticket System");
            System.out.println("5. Check Ticket System Status");
            System.out.println("6. Exit");
            System.out.print("Enter command: ");
            String command = scanner.nextLine().trim().toUpperCase();

            switch (command) {
                case "1":
                    // Call to configure ticket system
                    configureTicketSystem(ticketManager);
                    break;

                case "2":
                    // Call to configure VIP customers
                    configureVIPCustomers(ticketManager);
                    break;

                case "3":
                    // Start Ticket System
                    ticketManager.startOperations();
                    break;

                case "4":
                    // Stop Ticket System
                    ticketManager.stopOperations();
                    break;

                case "5":
                    // Check Ticket System Status
                    checkTicketSystemStatus(ticketManager);
                    break;

                case "6":
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

    private static void configureVIPCustomers(TicketManager ticketManager) {
        System.out.println("Configuring VIP Customers...");

        // Allow user to modify VIP customer configuration, for example:
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter VIP Customer ID: ");
        int vipCustomerId = scanner.nextInt();

        System.out.print("Enter VIP Customer Name: ");
        String vipCustomerName = scanner.next();

        // Update TicketManager with new VIP customer configuration
        ticketManager.addVIPCustomer(vipCustomerId, vipCustomerName);

        System.out.println("VIP Customer configuration updated.");
    }

    private static void checkTicketSystemStatus(TicketManager ticketManager) {
        if (ticketManager.isRunning()) {
            System.out.println("Ticket system is currently running.");
        } else {
            System.out.println("Ticket system is currently stopped.");
        }

        System.out.println("Vendor released tickets: " + ticketManager.getVendorReleasedTickets());
        System.out.println("Customer bought tickets: " + ticketManager.getCustomerBoughtTickets());
        System.out.println("System available tickets: " + ticketManager.getAvailableTickets());
        System.out.println("Currently available tickets: " + ticketManager.getCurrentlyAvailableTickets());
    }
}