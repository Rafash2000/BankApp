package person;

import bank.Bank;
import data.database.OracleDAO;
import data.Data;

import java.math.BigDecimal;

public class EmployeeImpl extends Person implements Employee{
    public EmployeeImpl(int id, String name, String surname, String login, String password, PersonType type) {
        super(id, name, surname, login, password, type);
    }

    @Override
    public void addAccount() {
        //Downloading client data
        var clientData = Data.getPersonData(type);

        //Account password generation
        var pass = Data.generatePassword();

        System.out.println("Haslo do konta: " + pass);

        //Adding a customer to the database
        OracleDAO.addClient(clientData[0], clientData[1], clientData[2], pass);
        System.out.println("Konto zostalo zalozone");
    }

    @Override
    public void deleteAccount() {
        //Retrieving the number of the customer account that we want to delete
        var numberAccount = Data.getNumberAccount("Numer konta: ");

        //Checking if the given account number is in the database
        //If so, we delete the account
        var isAccount = Bank.clients
                .stream()
                .anyMatch(c -> c.isAccountNumber(numberAccount));

        if (isAccount) {
            OracleDAO.deleteAccountByNumberAccount(numberAccount);
            Bank.clients = OracleDAO.getDataClientFromOracle();
            System.out.println("Konto zostalo usuniete");
        } else {
            System.out.println("Brak konta o podanym numerze konta");
        }
    }

    @Override
    public void payment() {
        //We collect the number of the account to which we want to deposit money
        var numberAccount = Data.getNumberAccount("Numer konta: ");

        //We check whether the account number is in the database
        //If so, we collect the amount we want to deposit into the account and update the database
        var isAccount = Bank.clients
                .stream()
                .anyMatch(c -> c.isAccountNumber(numberAccount));

        if (isAccount) {
            var amount = Data.getPositiveBigDecimal("Kwota: ");

            OracleDAO.updateBalanceByNumberAccount(numberAccount, amount);
            Bank.clients
                    .stream()
                    .filter(c -> c.isAccountNumber(numberAccount))
                    .forEach(c -> c.payment(amount));

            System.out.println("Kwota zostala wplacona na konto");
        } else {
            System.out.println("Brak wystarczajacych srodkow na koncie");
        }
    }

    @Override
    public void payout() {
        //We collect the number of the account from which we want to withdraw money
        var numberAccount = Data.getNumberAccount("Numer konta: ");

        //We check whether the account number is in the database
        //If so, we charge the amount and withdraw the amount from the account if possible
        var isAccount = Bank.clients
                .stream()
                .anyMatch(c -> c.isAccountNumber(numberAccount));


        if (isAccount) {
            var amount = Data.getPositiveBigDecimal("Kwota: ");

            BigDecimal finalAmount = amount;
            var confirmPayout = Bank.clients
                    .stream()
                    .filter(c -> c.isAccountNumber(numberAccount))
                    .anyMatch(c -> c.isPayout(finalAmount));


            if (confirmPayout) {
                amount = amount.multiply(new BigDecimal("-1"));

                OracleDAO.updateBalanceByNumberAccount(numberAccount, amount);

                System.out.println("Kwota zostala wyplacona z konto");
            } else {
                System.out.println("Brak wystarczajacych srodkow na koncie");
            }


        } else {
            System.out.println("Brak konta o podanym numerze");
        }
    }

    @Override
    public boolean isLogin(String employeeLogin) {
        return login.equals(employeeLogin);
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
