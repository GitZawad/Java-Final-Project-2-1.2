package FinanceApp.Models.Admin.CategoryManagement;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static FinanceApp.Main.Main.connection;
import static FinanceApp.Main.Main.scanner;

public class Create {

    static void createCategory(String type) {
        JFrame frame = new JFrame("Create Category");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(3, 2));

        JLabel nameLabel = new JLabel("Category Name:");
        JTextField nameField = new JTextField();
        frame.add(nameLabel);
        frame.add(nameField);

        JLabel typeLabel = new JLabel("Category Type:");
        JLabel typeValue = new JLabel(type);
        frame.add(typeLabel);
        frame.add(typeValue);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String categoryName = nameField.getText().trim();
            if (!categoryName.isEmpty()) {
                try {
                    String query = "INSERT INTO categories (name, type) VALUES (?, ?)";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setString(1, categoryName);
                    ps.setString(2, type);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(frame, "Category added successfully!");
                    frame.dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error adding category.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Category name cannot be empty.");
            }
        });
        frame.add(submitButton);

        frame.setVisible(true);
    }
}
