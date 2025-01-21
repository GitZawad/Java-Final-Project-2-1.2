# Finance Management System

A Java-based Finance Management System designed to allow users to track their income, expenditure, and balance, with distinct roles for administrators and users. The system is backed by a MySQL database and allows for various management tasks, including user and category management. This project was done during my first semester of the second year of undergraduation of software engineering. This college project was provided by the university as the final project of the Object Oriented Programming(OOP) Techniques along with three other projects. We were required to select one of the topic for the submission of the project.

## Features

- **Admin Role:**
  - **User Management:** Create, delete, and view users.
  - **Category Management:** Add and view income and expenditure categories.
  
- **User Role:**
  - **Record Management:** Add income and expenditure records.
  - **Balance Calculation:** Calculate total income, expenditure, and balance.
  - **View Records:** View added income and expenditure records.

## Technologies Used

- **Java**: Core programming language for developing the application.
- **MySQL**: Database for storing users, records, categories, and other related data.
- **JDBC**: Java Database Connectivity for interacting with the MySQL database.

## Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/your-username/finance-management.git
   ```

2. **Set up MySQL Database:**

   - Create a database named `finance_db`.
   - Run the following SQL queries to set up the required tables:

   ```sql
   CREATE TABLE users (
       user_id VARCHAR(50) PRIMARY KEY,
       password VARCHAR(100),
       role VARCHAR(50)
   );

   CREATE TABLE categories (
       category_id INT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(100),
       type VARCHAR(50)
   );

   CREATE TABLE records (
       record_id INT AUTO_INCREMENT PRIMARY KEY,
       user_id VARCHAR(50),
       category_id INT,
       amount DOUBLE,
       record_date DATE,
       FOREIGN KEY (user_id) REFERENCES users(user_id),
       FOREIGN KEY (category_id) REFERENCES categories(category_id)
   );
   ```

3. **Configure Database Credentials:**
   - In the `Main` class, replace the database credentials in the following line with your own:
   
   ```java
   connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finance_db", "root", "password");
   ```

4. **Run the Application:**

   - Compile and run the `Main` class to start the application.

   ```bash
   javac FinanceApp/Main/Main.java
   java FinanceApp.Main.Main
   ```

## Usage

### Roles:
- **Administrator**:
  - Login as an admin to manage users and categories.
  - Options to create, delete, and view users.
  - Manage income and expenditure categories.
  
- **User**:
  - Login with a user account to manage personal records.
  - Add income and expenditure records.
  - View records and calculate total income, expenditure, and balance.

### Example Flow:
1. **Login**: Enter your user ID and password.
2. **Menu**:
   - **Admin**: Choose between user management, income category management, or expenditure category management.
   - **User**: Choose between adding income/expenditure records, viewing records, or calculating balance.

## Structure

Here's the relation between the different packages and classes to organize the whole project


src/
├── FinanceApp/
│   ├── Main/
│   │   └── Main.java                    # Entry point of the application.
│   │   └── Role.java                    # Contains common roles logic.
│   ├── Models/
│   │   ├── Admin/
│   │   │   ├── CategoryManagement/
│   │   │   │   ├── Create.java          # Handles category creation for admin.
│   │   │   │   ├── Expenditure.java     # Manages expenditure-related tasks for admin.
│   │   │   │   ├── Income.java          # Manages income-related tasks for admin.
│   │   │   │   └── View.java            # Provides category-related views for admin.
│   │   │   ├── UserManagement/
│   │   │   │   ├── CreateUser.java      # Allows admin to create new users.
│   │   │   │   ├── DeleteUser.java      # Allows admin to delete existing users.
│   │   │   │   ├── UserManagement.java  # Main management interface for users by admin.
│   │   │   │   ├── ViewUsers.java       # Allows admin to view all users.
│   │   │   ├── AdminMenu.java           # Admin menu interface.
│   │   │   └── AdminRole.java           # Defines the admin role and permissions.
│   │   └── User/
│   │       ├── RecordManagement/
│   │       │   ├── Income.java          # Handles income record management for users.
│   │       │   ├── Expenditure.java     # Handles expenditure record management for users.
│   │       │   ├── View.java            # Allows users to view their records.
│   │       │   └── CalculateBalance.java# Calculates user's financial balance.
│   │       ├── UserMenu.java            # Provides user-specific menu options.
│   │       └── UserRole.java            # Defines the user role and permissions.


The project is organized into several packages:

- **`FinanceApp.Main`**: Main application flow and role-based login.
- **`FinanceApp.Models.Admin`**: Admin functionality (user management, category management).
- **`FinanceApp.Models.User`**: User functionality (record management, balance calculation).
- **`FinanceApp.Models.User.RecordManagement`**: Specific operations related to user records (adding/viewing income and expenditure).

## Contribution

Feel free to fork the repository, make improvements, and submit pull requests. Contributions are welcome!

