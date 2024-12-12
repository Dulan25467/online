package com.ticketingsystem.cli;

import com.ticketingsystem.producerconsumer.TicketManager;
import com.ticketingsystem.util.DatabaseUtil;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class TicketSystemMain {
    private static final Logger logger = Logger.getLogger(TicketSystemMain.class.getName());
    private static FileHandler fileHandler;
    private static ConsoleHandler consoleHandler;

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
            // Ensure the directory exists
            File logDir = new File("com/ticketingsystem/logs");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            // Set up logging to write to ticketing.log with a custom JSON formatter
            fileHandler = new FileHandler("com/ticketingsystem/logs/ticketing.log", true);
            fileHandler.setFormatter(new JSONFormatter());
            logger.addHandler(fileHandler);

            // Set up console logging
            consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new JSONFormatter());
            logger.addHandler(consoleHandler);
        } catch (IOException e) {
            System.out.println("Error setting up log file: " + e.getMessage());
            return;  // Exit if there's an error setting up the logger
        }

        // Create the ticket table if it doesn't exist
        DatabaseUtil.createTicketTable();

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
        int vipCustomerId = -1;

        while (true) {
            System.out.print("Enter VIP Customer ID: ");
            if (scanner.hasNextInt()) {
                vipCustomerId = scanner.nextInt();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid integer for VIP Customer ID.");
                scanner.next(); // Clear the invalid input
            }
        }

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
    public class Main {
        public static void main(String[] args) {
            DatabaseUtil.createTicketTable();
            TicketManager ticketManager = new TicketManager(100, 50, 5, 3);
            ticketManager.startOperations();
        }
    }
}