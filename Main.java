package FinanceApp.Main;

import FinanceApp.Models.Admin.AdminRole;
import FinanceApp.Models.User.UserRole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static Connection connection;
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        // Initialize MySQL connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finance_db", "your_username", "your_pass");
            System.out.println("Connected to MySQL Database.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Admin account pre-setup
        try {
            String query = "INSERT IGNORE INTO users (user_id, password, role) VALUES ('001', '001', 'admin')";
            connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Launch GUI
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Finance Assistant");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(3, 1));

        JLabel welcomeLabel = new JLabel("Welcome to the Finance Assistant", SwingConstants.CENTER);
        JButton adminButton = new JButton("Administrator");
        JButton userButton = new JButton("User");

        adminButton.addActionListener(e -> handleRoleSelection("admin"));
        userButton.addActionListener(e -> handleRoleSelection("user"));

        frame.add(welcomeLabel);
        frame.add(adminButton);
        frame.add(userButton);

        frame.setVisible(true);

        // Add shutdown hook to close database connection
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                    System.out.println("Database connection closed.");
                }
            } catch (SQLException e) {
                System.err.println("Failed to close database connection: " + e.getMessage());
            }
        }));
    }

    private static void handleRoleSelection(String role) {
        JFrame loginFrame = new JFrame(role.substring(0, 1).toUpperCase() + role.substring(1) + " Login");
        loginFrame.setSize(350, 200);
        loginFrame.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel idLabel = new JLabel("User ID:");
        JTextField idField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener((ActionEvent e) -> {
            Role userRole;

            if (role.equals("admin")) {
                userRole = new AdminRole(connection);
            } else {
                userRole = new UserRole(connection);
            }

            // Attempt login
            if (userRole.login()) {
                loginFrame.dispose();
                userRole.displayMenu();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid credentials! Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginFrame.add(idLabel);
        loginFrame.add(idField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(new JLabel()); // Empty cell for spacing
        loginFrame.add(loginButton);

        loginFrame.setVisible(true);
    }

}
