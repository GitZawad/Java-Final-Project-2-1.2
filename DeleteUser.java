package FinanceApp.Models.Admin.UserManagement;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static FinanceApp.Main.Main.connection;
import static FinanceApp.Main.Main.scanner;

public class DeleteUser {
    // GUI for deleting a user
    public static void deleteUserGUI() {
        JFrame frame = new JFrame("Delete User");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel idLabel = new JLabel("User ID:");
        JTextField idField = new JTextField();
        JButton deleteButton = new JButton("Delete User");
        JButton cancelButton = new JButton("Cancel");

        deleteButton.addActionListener(e -> {
            String userId = idField.getText().trim();

            try {
                String query = "DELETE FROM users WHERE user_id = ?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, userId);
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(frame, "User deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                frame.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error deleting user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> frame.dispose());

        frame.add(idLabel);
        frame.add(idField);
        frame.add(new JLabel());  // Empty cell for spacing
        frame.add(deleteButton);
        frame.add(new JLabel());  // Empty cell for spacing
        frame.add(cancelButton);

        frame.setVisible(true);
    }
}
