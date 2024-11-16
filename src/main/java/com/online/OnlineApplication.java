package com.online;

import com.online.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Lazy;

import java.io.Console;
import java.util.Scanner;

@ComponentScan(basePackages = {"com.online.service", "com.online.repository", "com.online.controller"})
@SpringBootApplication
public class OnlineApplication implements CommandLineRunner {

    private final UserService userService;

    @Autowired
    public OnlineApplication(@Lazy UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(OnlineApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        Console console = System.console(); // Get the console for password masking
        String command;

        while (true) {
            System.out.println("Enter command: ");
            System.out.println("(1) Register");
            System.out.println("(2) Login");
            System.out.println("(3) Exit");

            command = scanner.nextLine().trim().toLowerCase();

            if (command.equals(")")) {
                System.out.println("Exiting... Goodbye!");
                scanner.close();
                System.exit(0);
            }

            switch (command) {
                case "1":
                case "register":
                    String username;
                    do {
                        System.out.print("Username: ");
                        username = scanner.nextLine().trim();
                        if (username.equals("exit")) {
                            System.out.println("Exiting... Goodbye!");
                            scanner.close();
                            System.exit(0);
                        }

                        if (!username.matches("[a-zA-Z]+")) {
                            System.out.println("Username should contain only letters. Please try again.");
                        } else if (userService.isUsernameTaken(username)) {
                            System.out.println("Username already exists. Please choose a different username.");
                        } else {
                            break;
                        }
                    } while (true);

                    String password;
                    String confirmPassword;

                    do {
                        System.out.print("Password: ");
                        password = scanner.nextLine(); // Read the password input as a string

                        System.out.print("Confirm Password: ");
                        confirmPassword = scanner.nextLine(); // Read the confirm password input as a string

                        if (password.equals("exit") || confirmPassword.equals("exit")) {
                            System.out.println("Exiting... Goodbye!");
                            scanner.close();
                            System.exit(0);
                        }

                        if (!password.equals(confirmPassword)) {
                            System.out.println("Passwords do not match. Please try again.");
                        } else {
                            System.out.println("Password confirmed successfully!");
                            break;
                        }
                    } while (true);

                    String email;
                    do {
                        System.out.print("Email: ");
                        email = scanner.nextLine().trim();
                        if (email.equals("exit")) {
                            System.out.println("Exiting... Goodbye!");
                            scanner.close();
                            System.exit(0);
                        }

                        if (!email.contains("@")) {
                            System.out.println("Invalid email. Please try again.");
                        } else {
                            break;
                        }
                    } while (true);

                    System.out.print("Address: ");
                    String address = scanner.nextLine().trim();

                    String phone;
                    int phoneInt; // Declare the phoneInt variable outside the loop
                    do {
                        System.out.print("Phone Number: ");
                        phone = scanner.nextLine().trim();
                        if (phone.equals("exit")) {
                            System.out.println("Exiting... Goodbye!");
                            scanner.close();
                            System.exit(0);
                        }

                        // Validate that the phone number matches the 10-digit format
                        if (!phone.matches("\\d{10}")) {
                            System.out.println("Phone number must be exactly 10 digits. Please try again.");
                        } else {
                            // If valid, convert the phone string to an integer
                            phoneInt = Integer.parseInt(phone);
                            break; // Exit the loop if the input is valid
                        }
                    } while (true);

                    try {
                        userService.registerUser(username, password, email, phoneInt, address);
                        System.out.println("User registered successfully!");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "2":
                case "login":
                    boolean loginSuccess = false;
                    while (!loginSuccess) {
                        System.out.print("Username: ");
                        String loginUsername = scanner.nextLine().trim();
                        if (loginUsername.equals("exit")) {
                            System.out.println("Exiting... Goodbye!");
                            scanner.close();
                            System.exit(0);
                        }

                        System.out.print("Password: ");
                        char[] loginPasswordChars = console.readPassword();
                        String loginPassword = new String(loginPasswordChars);

                        if (loginPassword.equals("exit")) {
                            System.out.println("Exiting... Goodbye!");
                            scanner.close();
                            System.exit(0);
                        }

                        boolean isValid = userService.validateUser(loginUsername, loginPassword);
                        if (isValid) {
                            System.out.println("Login successful!");
                            loginSuccess = true;
                        } else {
                            System.out.println("Invalid credentials. Try again or type 'exit' to go back.");
                            String retry = scanner.nextLine().trim().toLowerCase();
                            if (retry.equals("exit")) {
                                break;
                            }
                        }
                    }
                    break;

                case "3":
                case "exit":
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Unknown command. Please enter '1', '2', or '3' for register, login, or exit respectively.");
            }
        }
    }

}
