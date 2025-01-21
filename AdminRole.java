package FinanceApp.Models.Admin;

import FinanceApp.Main.Role;
import FinanceApp.Models.Admin.CategoryManagement.Expenditure;
import FinanceApp.Models.Admin.CategoryManagement.Income;
import FinanceApp.Models.Admin.UserManagement.UserManagement;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class AdminRole extends Role {
    public AdminRole(Connection connection) {
        super(connection, "admin");
    }

    @Override
    public void displayMenu() {
        JFrame frame = new JFrame("Admin Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 1));

        JLabel welcomeLabel = new JLabel("Admin Menu", SwingConstants.CENTER);
        frame.add(welcomeLabel);

        JButton userManagementButton = new JButton("User Management");
        userManagementButton.addActionListener(e -> {
            UserManagement.userManagement(); // Assuming UserManagement uses GUI
        });
        frame.add(userManagementButton);

        JButton incomeCategoryButton = new JButton("Income Category Management");
        incomeCategoryButton.addActionListener(e -> {
            Income.incomeCategoryManagement(); // Adjust accordingly
        });
        frame.add(incomeCategoryButton);

        JButton expenditureCategoryButton = new JButton("Expenditure Category Management");
        expenditureCategoryButton.addActionListener(e -> {
            Expenditure.expenditureCategoryManagement(); // Adjust accordingly
        });
        frame.add(expenditureCategoryButton);

        JButton logoutButton = new JButton("LOGOUT");
        logoutButton.addActionListener(e -> frame.dispose());
        frame.add(logoutButton);

        frame.setVisible(true);
    }
}


