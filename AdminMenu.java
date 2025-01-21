package FinanceApp.Models.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static FinanceApp.Models.Admin.CategoryManagement.Expenditure.expenditureCategoryManagement;
import static FinanceApp.Models.Admin.CategoryManagement.Income.incomeCategoryManagement;
import static FinanceApp.Models.Admin.UserManagement.UserManagement.userManagement;

public class AdminMenu {

    public static void adminMenu() {
        JFrame frame = new JFrame("Admin Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 1));

        JLabel welcomeLabel = new JLabel("Admin Menu", SwingConstants.CENTER);
        frame.add(welcomeLabel);

        JButton userManagementButton = new JButton("User Management");
        userManagementButton.addActionListener((ActionEvent e) -> userManagement());
        frame.add(userManagementButton);

        JButton incomeCategoryButton = new JButton("Income Category Management");
        incomeCategoryButton.addActionListener((ActionEvent e) -> incomeCategoryManagement());
        frame.add(incomeCategoryButton);

        JButton expenditureCategoryButton = new JButton("Expenditure Category Management");
        expenditureCategoryButton.addActionListener((ActionEvent e) -> expenditureCategoryManagement());
        frame.add(expenditureCategoryButton);

        JButton exitButton = new JButton("EXIT");
        exitButton.addActionListener((ActionEvent e) -> frame.dispose());
        frame.add(exitButton);

        frame.setVisible(true);
    }
}
