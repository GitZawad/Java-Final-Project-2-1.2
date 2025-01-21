package FinanceApp.Main;

import java.sql.*;
import java.util.Scanner;

public abstract class Role {
    protected Connection connection;
    protected Scanner scanner = new Scanner(System.in);
    protected String roleName;

    public Role(Connection connection, String roleName) {
        this.connection = connection;
        this.roleName = roleName;
    }

    // Common login logic for both Admin and User
    public boolean login() {
        System.out.println("Please enter your account number:");
        String username = scanner.nextLine();
        System.out.println("Please enter your password:");
        String password = scanner.nextLine();

        try {
            String query = "SELECT * FROM users WHERE user_id = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if (rs.getString("role").equals(roleName)) {
                    System.out.println("Welcome " + roleName + ".");
                    return true;
                } else {
                    System.out.println("Incorrect role.");
                    return false;
                }
            } else {
                System.out.println("Username or password is incorrect.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Abstract method to be implemented by subclasses to handle menu options
    public abstract void displayMenu();
}
