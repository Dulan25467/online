package com.ticketingsystem.cli;

import com.ticketingsystem.producerconsumer.TicketManager;

import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class TicketSystemCLI extends TicketSystemMain {
    private static final Logger logger = Logger.getLogger(TicketSystemCLI.class.getName());
    private static FileHandler fileHandler;

    // Custom JSON formatter
    static class JSONFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {
            StringBuilder sb = new StringBuilder();
            sb.append("{")
                    .append("\"timestamp\": \"").append(record.getMillis()).append("\", ")
                    .append("\"level\": \"").append(record.getLevel()).append("\", ")
                    .append("\"message\": \"").append(formatMessage(record)).append("\"")
                    .append("}\n");
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        try {
            // Set up logging to write to ticketing.log with a custom JSON formatter
            fileHandler = new FileHandler("ticketing.log", true);
            fileHandler.setFormatter(new JSONFormatter());
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            System.out.println("Error setting up log file: " + e.getMessage());
            return;  // Exit if there's an error setting up the logger
        }

        Scanner scanner = new Scanner(System.in);

        // Get the total tickets available
        System.out.print("Enter total tickets available: ");
        int totalTickets = scanner.nextInt();
        logger.info("Total tickets available: " + totalTickets);

        // Get the ticket release rate
        System.out.print("Enter ticket release rate (per second): ");
        int ticketReleaseRate = scanner.nextInt();
        logger.info("Ticket release rate (per second): " + ticketReleaseRate);

        // Get the customer retrieval rate
        System.out.print("Enter customer retrieval rate (per second): ");
        int customerRetrievalRate = scanner.nextInt();
        logger.info("Customer retrieval rate (per second): " + customerRetrievalRate);

        // Get the max ticket capacity
        System.out.print("Enter max ticket capacity: ");
        int maxTicketCapacity = scanner.nextInt();
        logger.info("Max ticket capacity: " + maxTicketCapacity);

        // Initialize the ticket manager with configuration values
        TicketManager ticketManager = new TicketManager(maxTicketCapacity, totalTickets, ticketReleaseRate, customerRetrievalRate);

        // Ensure the logs are immediately written to the file by flushing the FileHandler
        fileHandler.flush();

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
                    configureTicketSystem(ticketManager);
                    break;

                case "2":
                    configureVIPCustomers(ticketManager);
                    break;

                case "3":
                    ticketManager.startOperations();
                    break;

                case "4":
                    ticketManager.stopOperations();
                    break;

                case "5":
                    checkTicketSystemStatus(ticketManager);
                    break;

                case "6":
                    if (ticketManager.isRunning()) {
                        ticketManager.stopOperations();
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

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Max Ticket Capacity: ");
        int maxTicketCapacity = scanner.nextInt();

        System.out.print("Enter Total Tickets Available: ");
        int totalTicketsAvailable = scanner.nextInt();

        System.out.print("Enter Ticket Release Rate: ");
        int ticketReleaseRate = scanner.nextInt();

        System.out.print("Enter Customer Retrieval Rate: ");
        int customerRetrievalRate = scanner.nextInt();

        ticketManager.updateConfiguration(maxTicketCapacity, totalTicketsAvailable, ticketReleaseRate, customerRetrievalRate);

        System.out.println("Ticket System configuration updated.");
    }

    private static void configureVIPCustomers(TicketManager ticketManager) {
        System.out.println("Configuring VIP Customers...");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter VIP Customer ID: ");
        int vipCustomerId = scanner.nextInt();

        System.out.print("Enter VIP Customer Name: ");
        String vipCustomerName = scanner.next();

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