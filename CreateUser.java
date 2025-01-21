package FinanceApp.Models.Admin.UserManagement;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class CreateUser {
    public void createUser(Connection connection, Scanner scanner) {
        JFrame frame = new JFrame("Create New User");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel idLabel = new JLabel("User ID:");
        JTextField idField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton createButton = new JButton("Create User");
        JButton cancelButton = new JButton("Cancel");

        createButton.addActionListener(e -> {
            String userId = idField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            try {
                String query = "INSERT INTO users (user_id, password, role) VALUES (?, ?, 'user')";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, userId);
                ps.setString(2, password);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(frame, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error adding user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> frame.dispose());

        frame.add(idLabel);
        frame.add(idField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(new JLabel());  // Empty cell for spacing
        frame.add(createButton);
        frame.add(new JLabel());  // Empty cell for spacing
        frame.add(cancelButton);

        frame.setVisible(true);
    }
}
