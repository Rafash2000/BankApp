package data.database;

import person.AdminImpl;
import person.ClientImpl;
import person.EmployeeImpl;
import person.PersonType;

import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class OracleDAO {
    private static String clientTable = "client";
    private static String employeeTable = "employee";
    private static String adminTable = "admin";
    private static String[] column = {"id", "name", "surname", "login", "password", "balance", "number_account"};

    //Downloading the list of clients from the database
    public static List<ClientImpl> getDataClientFromOracle() {
        var clients = new LinkedList<ClientImpl>();
        try (var connection = OracleJDBC.getConnection()) {

            var sql = "SELECT * FROM client";
            var statement = connection.prepareStatement(sql);
            var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                var id = resultSet.getInt(column[0]);
                var name = resultSet.getString(column[1]);
                var surname = resultSet.getString(column[2]);
                var login = resultSet.getString(column[3]);
                var password = resultSet.getString(column[4]);
                var cash = resultSet.getBigDecimal(column[5]);
                var number_account = resultSet.getString(column[6]);


                clients.add(new ClientImpl(id, name, surname, login, password, number_account, cash, PersonType.CLIENT));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    //Updating the status of the customer's account with the given account number
    public static void updateBalanceByNumberAccount(String numberAccount, BigDecimal amount) {
        try {
            var connection = OracleJDBC.getConnection();

            var sql = "UPDATE client SET balance = balance + ? WHERE number_account = ?";
            var statement = connection.prepareStatement(sql);
            statement.setBigDecimal(1, amount);
            statement.setInt(2, Integer.parseInt(numberAccount));

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Changing the status of the customer account with the given id number
    public static void updateBalanceById(int id, BigDecimal newBalance) {
        try {
            var connection = OracleJDBC.getConnection();

            var sql = "UPDATE client SET balance = ? WHERE id = ?";
            var statement = connection.prepareStatement(sql);
            statement.setBigDecimal(1, newBalance);
            statement.setInt(2, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Changing the password of the user with the given id and personType
    public static void updatePassword(int id, String password, PersonType personType) {
        try {
            var connection = OracleJDBC.getConnection();

            var sql = "";

            //We check which table the user is in
            switch (personType) {
                case CLIENT -> sql = "UPDATE client SET password = ? WHERE id = ?";
                case EMPLOYEE -> sql = "UPDATE employee SET password = ? WHERE id = ?";
                case ADMIN -> sql = "UPDATE admin SET password = ? WHERE id = ?";
            }

            var statement = connection.prepareStatement(sql);
            statement.setString(1, password);
            statement.setInt(2, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Adding a new customer to the database
    public static void addClient(String name, String surname, String login, String password) {
        try {
            var connection = OracleJDBC.getConnection();

            String sql = "INSERT INTO client (name, surname, login, password) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, login);
            statement.setString(4, password);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Removing a customer with a given account number from the database
    public static void deleteAccountByNumberAccount(String numberAccount) {
        try {
            var connection = OracleJDBC.getConnection();

            var sql = "DELETE FROM client WHERE number_account = ?";
            var statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(numberAccount));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //Downloading the list of employees
    public static List<EmployeeImpl> getDataEmployeeFromOracle() {
        var employees = new LinkedList<EmployeeImpl>();
        try (Connection connection = OracleJDBC.getConnection()) {

            var sql = "SELECT * FROM employee";
            var statement = connection.prepareStatement(sql);
            var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                var id = resultSet.getInt(column[0]);
                var name = resultSet.getString(column[1]);
                var surname = resultSet.getString(column[2]);
                var login = resultSet.getString(column[3]);
                var password = resultSet.getString(column[4]);

                employees.add(new EmployeeImpl(id, name, surname, login, password, PersonType.EMPLOYEE));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    //Downloading the list of admins from the database
    public static List<AdminImpl> getDataAdminFromOracle() {
        var admins = new LinkedList<AdminImpl>();
        try (Connection connection = OracleJDBC.getConnection()) {

            var sql = "SELECT * FROM admin";
            var statement = connection.prepareStatement(sql);
            var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                var id = resultSet.getInt(column[0]);
                var name = resultSet.getString(column[1]);
                var surname = resultSet.getString(column[2]);
                var login = resultSet.getString(column[3]);
                var password = resultSet.getString(column[4]);


                admins.add(new AdminImpl(id, name, surname, login, password, PersonType.ADMIN));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    //Adding a new employee to the database
    public static void addEmployee(String name, String surname, String login, String password) {
        try {
            var connection = OracleJDBC.getConnection();

            var sql = "INSERT INTO employee (name, surname, login, password) VALUES (?, ?, ?, ?)";
            var statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, login);
            statement.setString(4, password);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Adding a new admin to the database
    public static void addAdmin(String name, String surname, String login, String password) {
        try {
            var connection = OracleJDBC.getConnection();

            var sql = "INSERT INTO admin (name, surname, login, password) VALUES (?, ?, ?, ?)";
            var statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, login);
            statement.setString(4, password);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Removal of the employee with the given login from the database
    public static void deleteEmployeeByLogin(String employeeLogin) {
        try {
            var connection = OracleJDBC.getConnection();

            var sql = "DELETE FROM employee WHERE login = ?";
            var statement = connection.prepareStatement(sql);
            statement.setString(1, employeeLogin);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Removing the admin with the given id from the database
    public static void deleteAdminById(int adminId) {
        try {
            var connection = OracleJDBC.getConnection();

            var sql = "DELETE FROM admin WHERE id = ?";
            var statement = connection.prepareStatement(sql);
            statement.setInt(1, adminId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
