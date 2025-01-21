package FinanceApp.Models.User.RecordManagement;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static FinanceApp.Main.Main.connection;

public class View {
    // View records
    public static void viewRecords() {
        try {
            String query = "SELECT r.amount, r.record_date, c.name AS category FROM records r " +
                    "JOIN categories c ON r.category_id = c.category_id " +
                    "WHERE r.user_id = 'user01'"; // Assuming user01 for now

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel(new String[]{"Category", "Amount", "Date"}, 0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("category"),
                        rs.getDouble("amount"),
                        rs.getString("record_date")
                });
            }

            if (model.getRowCount() > 0) {
                JTable table = new JTable(model);
                JScrollPane scrollPane = new JScrollPane(table);
                JOptionPane.showMessageDialog(null, scrollPane, "View Records", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No records available.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching records: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
