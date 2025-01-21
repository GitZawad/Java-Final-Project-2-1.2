package FinanceApp.Models.User.RecordManagement;

import java.sql.*;
import javax.swing.*;

import static FinanceApp.Main.Main.connection;

public class CalculateBalance {
    // Calculate total income, expenditure, and balance
    public static void calculateBalance() {
        double totalIncome = 0, totalExpenditure = 0;

        try {
            // Calculate total income
            String incomeQuery = "SELECT SUM(amount) AS total FROM records r " +
                    "JOIN categories c ON r.category_id = c.category_id " +
                    "WHERE r.user_id = 'user01' AND c.type = 'income'";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(incomeQuery);
            if (rs.next()) {
                totalIncome = rs.getDouble("total");
            }

            // Calculate total expenditure
            String expenditureQuery = "SELECT SUM(amount) AS total FROM records r " +
                    "JOIN categories c ON r.category_id = c.category_id " +
                    "WHERE r.user_id = 'user01' AND c.type = 'expenditure'";
            rs = stmt.executeQuery(expenditureQuery);
            if (rs.next()) {
                totalExpenditure = rs.getDouble("total");
            }

            double balance = totalIncome - totalExpenditure;

            // Display results in a JOptionPane
            JOptionPane.showMessageDialog(null,
                    "Total Income: " + totalIncome + "\n" +
                            "Total Expenditure: " + totalExpenditure + "\n" +
                            "Balance: " + balance,
                    "Balance Calculation", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
