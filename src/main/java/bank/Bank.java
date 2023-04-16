package bank;

import data.database.OracleDAO;
import person.AdminImpl;
import person.ClientImpl;
import data.Data;
import person.EmployeeImpl;

import java.sql.SQLException;
import java.util.List;

public class Bank {
    public static List<ClientImpl> clients;
    public static List<EmployeeImpl> employees;
    public static List<AdminImpl> admins;
    public static void start() throws SQLException {
        //Downloading data to lists
        clients = OracleDAO.getDataClientFromOracle();
        employees = OracleDAO.getDataEmployeeFromOracle();
        admins = OracleDAO.getDataAdminFromOracle();

        System.out.println("1 - Klient banku");
        System.out.println("2 - Pracownik banku");
        System.out.println("3 - Admin");

        //Choose the account you want to log in to
        var option = Data.getInt();

        login(option);
    }

    private static void login(int option) throws SQLException {
        for (int i = 0; i < Data.loginLimit; i++) {
            switch (option) {
                case 1 -> {
                    //Downloading login and password
                    var data = Data.getLoginAndPassword();

                    //Checking if there is a client with given login and password in the list
                    //If so, we go to the client application
                    clients
                            .stream()
                            .filter(c -> c.checkLogin(data[0], data[1]))
                            .findFirst()
                            .ifPresent(Bank::clientApp);
                    System.out.println("Login lub haslo jest nieprawidlowe");
                }
                case 2 -> {
                    var data = Data.getLoginAndPassword();
                    employees
                            .stream()
                            .filter(e -> e.checkLogin(data[0], data[1]))
                            .findFirst()
                            .ifPresent(Bank::employeeApp);
                    System.out.println("Login lub haslo jest nieprawidlowe");
                }
                case 3 -> {
                    var data = Data.getLoginAndPassword();
                    admins
                            .stream()
                            .filter(a -> a.checkLogin(data[0], data[1]))
                            .findFirst()
                            .ifPresent(Bank::adminApp);
                    System.out.println("Login lub haslo jest nieprawidlowe");
                }
                default -> throw new IllegalStateException("Incorrect option");
            }
        }
    }




    private static void clientApp(ClientImpl client) {
        var option = 0;

        while (true) {
            System.out.println(client);

            System.out.println("1 - Przelew");
            System.out.println("2 - Zmien haslo");
            System.out.println("\n0 - Wyloguj");

            //Choose what we want to do
            option = Data.getInt();

            switch (option) {
                case 0 -> System.exit(0);
                case 1 -> {
                    System.out.println("Przelew");
                    client.bankTransfer();
                }
                case 2 -> {
                    System.out.println("Zmiana hasla");
                    client.changePassword();
                }
                default -> System.out.println("Brak wybranej opcji");
            }
        }
    }

    private static void employeeApp(EmployeeImpl employee) {
        var option = 0;

        while (true) {
            System.out.println(employee);

            System.out.println("1 - Zaloz konto bankowe");
            System.out.println("2 - Usun konto bankowe");
            System.out.println("3 - Wplac na konto");
            System.out.println("4 - Wyplac z konta");
            System.out.println("5 - Zmien haslo");
            System.out.println("\n0 - Wyloguj");

            option = Data.getInt();

            switch (option) {
                case 0 -> System.exit(0);
                case 1 -> {
                    System.out.println("Zakladanie konta");
                    employee.addAccount();
                    clients = OracleDAO.getDataClientFromOracle();
                }
                case 2 -> {
                    System.out.println("Usuwanie konta");
                    employee.deleteAccount();
                    clients = OracleDAO.getDataClientFromOracle();
                }
                case 3 -> {
                    System.out.println("Wplata");
                    employee.payment();
                }
                case 4 -> {
                    System.out.println("Wyplata");
                    employee.payout();
                }
                case 5 -> {
                    System.out.println("Zmiana hasla");
                    employee.changePassword();
                }
                default -> System.out.println("Brak wybranej opcji");
            }
        }
    }

    private static void adminApp(AdminImpl admin) {
        var option = 0;

        while (true) {
            System.out.println(admin);

            System.out.println("1 - Dodaj pracownika/admina");
            System.out.println("2 - Usun pracownika");
            System.out.println("3 - Zmien haslo");
            System.out.println("4 - Usun konto");
            System.out.println("\n0 - Wyloguj");

            option = Data.getInt();

            switch (option) {
                case 0 -> System.exit(0);
                case 1 -> {
                    System.out.println("Dodaj pracownika");
                    admin.addEmployeeOrAdmin();
                }
                case 2 -> {
                    System.out.println("Usun pracownika");
                    admin.deleteEmployee();
                }
                case 3 -> {
                    System.out.println("Zmien haslo");
                    admin.changePassword();
                }
                case 4 -> {
                    System.out.println("Usun konto");
                    admin.deleteAccount();
                }
                default -> throw new IllegalStateException("Incorrect option");
            }
        }
    }
}
