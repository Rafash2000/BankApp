package person;

import bank.Bank;
import data.database.OracleDAO;
import data.Data;

import java.math.BigDecimal;
import java.util.Objects;


public class ClientImpl extends Person implements Client{
    final private String numberAccount;
    private BigDecimal balance;

    public ClientImpl(int id, String name, String surname, String login, String password, String number_account, BigDecimal balance, PersonType type) {
        super(id, name, surname, login, password, type);
        this.numberAccount = number_account;
        this.balance = balance;
    }


    @Override
    public void bankTransfer() {
        //Transfer
        var number = Data.getNumberAccount("Numer konta: ");
        var amount = Data.getPositiveBigDecimal("Kwota przelewu: ");


        //We check if we have enough funds in the account to make the transfer
        if (amount.compareTo(balance) <= 0) {
            balance = balance.subtract(amount);

            //We check whether the given account number is in our database
            //If so, he makes a transfer to his account and update the database
            var isClient = Bank.clients
                    .stream()
                    .anyMatch(c -> c.isAccountNumber(number));

            if (isClient) {
                OracleDAO.updateBalanceByNumberAccount(number, amount);
            }

            OracleDAO.updateBalanceById(id, balance);

            System.out.println("Przelew wykonano");
        } else {
            System.out.println("Nie masz wystarczajaco srodkow na koncie");
        }
    }

    @Override
    public boolean isAccountNumber(String number) {
        return number.equals(numberAccount);
    }

    @Override
    public boolean isPayout(BigDecimal amount) {
        if (balance.compareTo(amount) >= 0) {
            balance = balance.subtract(amount);
            return true;
        }
        return false;
    }

    @Override
    public void payment(BigDecimal amount) {
        balance = balance.add(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientImpl client = (ClientImpl) o;
        return id == client.id && Objects.equals(name, client.name) && Objects.equals(surname, client.surname) && Objects.equals(numberAccount, client.numberAccount) && Objects.equals(balance, client.balance) && Objects.equals(login, client.login) && Objects.equals(password, client.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, numberAccount, balance, login, password);
    }

    @Override
    public String toString() {
        return super.toString() + "Numer konta: " + numberAccount + "\nStan Konta: " + balance + " zl\n";
    }
}
