package FinanceApp.Models.User;

import javax.swing.*;
import java.awt.*;
import java.sql.*;


import static FinanceApp.Main.Main.connection;
import static FinanceApp.Models.User.RecordManagement.CalculateBalance.calculateBalance;
import static FinanceApp.Models.User.RecordManagement.Expenditure.addExpenditureRecord;
import static FinanceApp.Models.User.RecordManagement.View.viewRecords;

public class UserMenu {
    // User menu
    public static void userMenu() {
        JFrame frame = new JFrame("User Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(6, 1));

        JLabel welcomeLabel = new JLabel("User Menu", SwingConstants.CENTER);
        frame.add(welcomeLabel);

        // Add Income Button
        JButton addIncomeButton = new JButton("Add income record");
        addIncomeButton.addActionListener(e -> showAddIncomeDialog(frame, connection));
        frame.add(addIncomeButton);

        // Add Expenditure Button
        JButton addExpenditureButton = new JButton("Add expenditure record");
        addExpenditureButton.addActionListener(e -> addExpenditureRecord());
        frame.add(addExpenditureButton);

        // View Records Button
        JButton viewRecordsButton = new JButton("View records");
        viewRecordsButton.addActionListener(e -> viewRecords());
        frame.add(viewRecordsButton);

        // Calculate Balance Button
        JButton calculateBalanceButton = new JButton("Calculate total income, expenditure, and balance");
        calculateBalanceButton.addActionListener(e -> calculateBalance());
        frame.add(calculateBalanceButton);

        // Exit Button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> frame.dispose());
        frame.add(exitButton);

        frame.setVisible(true);
    }

    // Method to display "Add Income Record" dialog
    private static void showAddIncomeDialog(JFrame parentFrame, Connection connection) {
        JTextField categoryField = new JTextField();
        JTextField amountField = new JTextField();
        JTextField dateField = new JTextField();

        Object[] message = {
                "Category:", categoryField,
                "Amount:", amountField,
                "Date (yyyy-MM-dd):", dateField
        };

        int option = JOptionPane.showConfirmDialog(parentFrame, message, "Enter Income Details", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String category = categoryField.getText().trim();
            String amountText = amountField.getText().trim();
            String date = dateField.getText().trim();

            if (category.isEmpty() || amountText.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double amount = Double.parseDouble(amountText);

                // Check if category exists
                String query = "SELECT category_id FROM categories WHERE name = ? AND type = 'income'";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, category);
                ResultSet rs = ps.executeQuery();

                int categoryId;
                if (rs.next()) {
                    categoryId = rs.getInt("category_id");
                } else {
                    // Insert new category
                    String insertCategoryQuery = "INSERT INTO categories (name, type) VALUES (?, 'income')";
                    PreparedStatement insertCategoryPs = connection.prepareStatement(insertCategoryQuery, Statement.RETURN_GENERATED_KEYS);
                    insertCategoryPs.setString(1, category);
                    insertCategoryPs.executeUpdate();

                    ResultSet generatedKeys = insertCategoryPs.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        categoryId = generatedKeys.getInt(1);
                    } else {
                        JOptionPane.showMessageDialog(parentFrame, "Failed to add category.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Insert income record
                String insertQuery = "INSERT INTO records (user_id, category_id, amount, record_date) VALUES (?, ?, ?, ?)";
                PreparedStatement insertPs = connection.prepareStatement(insertQuery);
                insertPs.setString(1, "user01"); // Example user ID
                insertPs.setInt(2, categoryId);
                insertPs.setDouble(3, amount);
                insertPs.setDate(4, java.sql.Date.valueOf(date));
                insertPs.executeUpdate();

                JOptionPane.showMessageDialog(parentFrame, "Income record added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parentFrame, "Amount must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(parentFrame, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

}
