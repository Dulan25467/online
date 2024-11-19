package com.ticketingsystem.cli;

import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class TicketSystemCLI extends TicketSystemMain{
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

        // Ensure the logs are immediately written to the file by flushing the FileHandler
        fileHandler.flush();

        System.out.println("Ticketing system initialized. Check the ticketing.log file for details.");
    }
}
