package FinanceApp.Models.User.RecordManagement;

import java.sql.*;
import javax.swing.*;

import static FinanceApp.Main.Main.connection;
import static FinanceApp.Main.Main.scanner;

public class Income {
    // Add income record
    public static void addIncomeRecord() {
        JTextField categoryField = new JTextField();
        JTextField amountField = new JTextField();
        JTextField dateField = new JTextField();

        JPanel panel = new JPanel();
        panel.add(new JLabel("Category:"));
        panel.add(categoryField);
        panel.add(Box.createVerticalStrut(15)); // a spacer
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(Box.createVerticalStrut(15)); // a spacer
        panel.add(new JLabel("Date (yyyy-mm-dd):"));
        panel.add(dateField);

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Enter Income Details", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String category = categoryField.getText().trim();
            double amount = Double.parseDouble(amountField.getText().trim());
            String date = dateField.getText().trim();

            try {
                // Check if category exists
                String query = "SELECT category_id FROM categories WHERE name = ? AND type = 'income'";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, category);
                ResultSet rs = ps.executeQuery();

                int categoryId;
                if (rs.next()) {
                    // Category exists, retrieve its ID
                    categoryId = rs.getInt("category_id");
                } else {
                    // Category does not exist, insert it
                    String insertCategoryQuery = "INSERT INTO categories (name, type) VALUES (?, 'income')";
                    PreparedStatement insertCategoryPs = connection.prepareStatement(insertCategoryQuery, Statement.RETURN_GENERATED_KEYS);
                    insertCategoryPs.setString(1, category);
                    insertCategoryPs.executeUpdate();

                    ResultSet generatedKeys = insertCategoryPs.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        categoryId = generatedKeys.getInt(1);
                        System.out.println("New category added with ID: " + categoryId);
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add new category.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Add income record
                String insertQuery = "INSERT INTO records (user_id, category_id, amount, record_date) VALUES (?, ?, ?, ?)";
                PreparedStatement insertPs = connection.prepareStatement(insertQuery);
                insertPs.setString(1, "user01"); // Using example user "user01"
                insertPs.setInt(2, categoryId);
                insertPs.setDouble(3, amount);
                insertPs.setDate(4, java.sql.Date.valueOf(date)); // Using java.sql.Date for date fields
                insertPs.executeUpdate();
                JOptionPane.showMessageDialog(null, "Income record added.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding income record.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
