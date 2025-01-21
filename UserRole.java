package FinanceApp.Models.User;

import FinanceApp.Main.Role;
import java.sql.Connection;

import static FinanceApp.Models.User.UserMenu.userMenu;

public class UserRole extends Role {

    public UserRole(Connection connection) {
        super(connection, "user"); // Pass the connection and role name to the base class
    }

    @Override
    public void displayMenu() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            userMenu(); // Calls the GUI-based user menu
        });
    }
}
