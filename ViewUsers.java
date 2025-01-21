package FinanceApp.Models.Admin.UserManagement;

import FinanceApp.Main.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class ViewUsers {

    static void viewAllUsers() {
        JFrame frame = new JFrame("View All Users");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("All Users", SwingConstants.CENTER);
        frame.add(titleLabel, BorderLayout.NORTH);

        DefaultListModel<String> userListModel = new DefaultListModel<>();
        JList<String> userList = new JList<>(userListModel);
        frame.add(new JScrollPane(userList), BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshUserList(userListModel));
        frame.add(refreshButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static void refreshUserList(DefaultListModel<String> userListModel) {
        userListModel.clear();
        try {
            String query = "SELECT user_id FROM users";
            Statement stmt = Main.connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                userListModel.addElement(rs.getString("user_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
