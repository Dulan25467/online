package com.ticketingsystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/ticketingsystem?createDatabaseIfNotExist=true";
    private static final String USER = "root";
    private static final String PASSWORD = "Dulan@25467";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void createTicketTable() {
        String sql = "CREATE TABLE IF NOT EXISTS ticket ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "ticketName VARCHAR(255) NOT NULL)";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Ticket table created or already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}