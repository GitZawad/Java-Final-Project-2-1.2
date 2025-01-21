package FinanceApp.Models.Admin.UserManagement;

import FinanceApp.Main.Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Scanner;

public class UserManagement {

    public static void userManagement() {
        JFrame frame = new JFrame("User Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 1, 10, 10));

        JLabel welcomeLabel = new JLabel("User Management", SwingConstants.CENTER);
        frame.add(welcomeLabel);

        JButton createUserButton = new JButton("Create User");
        createUserButton.addActionListener(e -> {
            CreateUser createUserInstance = new CreateUser();
            createUserInstance.createUser(Main.connection, new Scanner(System.in));
        });
        frame.add(createUserButton);

        JButton deleteUserButton = new JButton("Delete User");
        deleteUserButton.addActionListener(e -> DeleteUser.deleteUserGUI()); // Correct static method call
        frame.add(deleteUserButton);

        JButton viewUsersButton = new JButton("View All Users");
        viewUsersButton.addActionListener(e -> ViewUsers.viewAllUsers());
        frame.add(viewUsersButton);

        JButton exitButton = new JButton("EXIT");
        exitButton.addActionListener(e -> frame.dispose());
        frame.add(exitButton);

        frame.setVisible(true);
    }
}
