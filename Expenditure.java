package FinanceApp.Models.User.RecordManagement;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

import static FinanceApp.Main.Main.connection;
import static FinanceApp.Main.Main.scanner;

public class Expenditure {
    // Add expenditure record
    public static void addExpenditureRecord() {
        // Create input fields
        JTextField categoryField = new JTextField(15);
        JTextField amountField = new JTextField(15);
        JTextField dateField = new JTextField(15);

        // Create a panel with a grid layout for proper alignment
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Category:"));
        panel.add(categoryField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(new JLabel("Date (yyyy-mm-dd):"));
        panel.add(dateField);

        // Show the dialog
        int result = JOptionPane.showConfirmDialog(null, panel, "Enter Expenditure Details",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Validate and process input
            try {
                String category = categoryField.getText().trim();
                String amountText = amountField.getText().trim();
                String date = dateField.getText().trim();

                // Validate inputs
                if (category.isEmpty() || amountText.isEmpty() || date.isEmpty()) {
                    throw new IllegalArgumentException("All fields must be filled.");
                }

                double amount = Double.parseDouble(amountText);

                // Database logic
                String query = "SELECT category_id FROM categories WHERE name = ? AND type = 'expenditure'";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, category);
                ResultSet rs = ps.executeQuery();

                int categoryId;
                if (rs.next()) {
                    categoryId = rs.getInt("category_id");
                } else {
                    String insertCategoryQuery = "INSERT INTO categories (name, type) VALUES (?, 'expenditure')";
                    PreparedStatement insertCategoryPs = connection.prepareStatement(insertCategoryQuery, Statement.RETURN_GENERATED_KEYS);
                    insertCategoryPs.setString(1, category);
                    insertCategoryPs.executeUpdate();
                    ResultSet generatedKeys = insertCategoryPs.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        categoryId = generatedKeys.getInt(1);
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add category.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                String insertQuery = "INSERT INTO records (user_id, category_id, amount, record_date) VALUES (?, ?, ?, ?)";
                PreparedStatement insertPs = connection.prepareStatement(insertQuery);
                insertPs.setString(1, "user01"); // Assuming user01 for now
                insertPs.setInt(2, categoryId);
                insertPs.setDouble(3, amount);
                insertPs.setString(4, date);
                insertPs.executeUpdate();

                JOptionPane.showMessageDialog(null, "Expenditure record added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Amount must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding expenditure record.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
