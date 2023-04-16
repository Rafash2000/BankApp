package person;

import bank.Bank;
import data.database.OracleDAO;
import data.Data;

public class AdminImpl extends Person implements Admin{
    public AdminImpl(int id, String name, String surname, String login, String password, PersonType type) {
        super(id, name, surname, login, password, type);
    }

    @Override
    public void addEmployeeOrAdmin() {
        //We collect user data
        var personData = Data.getPersonData(type);

        //We generate a password
        var pass = Data.generatePassword();

        System.out.println("Haslo do konta: " + pass);

        //We add a new employee or admin to the database
        if (personData[3].equals(PersonType.ADMIN.toString())) {
            OracleDAO.addAdmin(personData[0], personData[1], personData[2], pass);
        } else if (personData[3].equals(PersonType.EMPLOYEE.toString())){
            OracleDAO.addEmployee(personData[0], personData[1], personData[2], pass);
            Bank.employees = OracleDAO.getDataEmployeeFromOracle();
        }

        System.out.println("Konto zostalo zalozone");
    }

    @Override
    public void deleteEmployee() {
        //We download the login of the employee we want to remove
        var employeeLogin = Data.getLogin("Login: ");

        //We check if it is in the database
        //If so, we remove it from the database
        var isLogin = Bank.employees
                .stream()
                .anyMatch(e -> e.isLogin(employeeLogin));

        if (isLogin) {
            OracleDAO.deleteEmployeeByLogin(employeeLogin);
            System.out.println("Pracownik zostal usuniety");
        } else {
            System.out.println("Brak pracownika o podanym loginie");
        }
    }

    @Override
    public void deleteAccount() {
        //We enter the account password to confirm that we want to delete the account
        //If we have entered the correct password, the account will be deleted
        System.out.println("Wpisz haslo, aby potwierdzic usuniecie konta");

        for (int i = 0; i < Data.loginLimit; i++) {
            var pass = Data.getPassword("Haslo: ");
            if (password.equals(pass)) {
                OracleDAO.deleteAdminById(id);
                System.out.println("Konto zostalo usuniete");
                System.exit(0);
            } else {
                System.out.println("Bledne haslo");
            }
        }
    }
}
