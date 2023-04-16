package data;

import bank.Bank;
import person.AdminImpl;
import person.ClientImpl;
import person.EmployeeImpl;
import person.PersonType;
import validator.Validator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Data {
    public static Scanner scanner = new Scanner(System.in);
    public static int loginLimit = 3;
    public static int getInt() {
        return Validator.isInt(scanner.nextLine());
    }

    public static BigDecimal getPositiveBigDecimal(String context) {
        String number;
        do {
            System.out.print(context);
            number = scanner.nextLine();

            if (!Validator.positiveBigDecimalValidator(number)) {
                System.out.println("Nieprawidlowa liczba");
            }
        } while (!Validator.positiveBigDecimalValidator(number));

        return new BigDecimal(number);
    }

    public static String[] getLoginAndPassword() {
        var data = new String[2];

        data[0] = getLogin("Login: ");
        data[1] = getPassword("Haslo: ");

        return data;
    }

    public static String[] getPersonData(PersonType personType) {
        var personData = new String[4];
        boolean isLogin;
        List<String> loginList = null;
        var option = 0;


        //Downloading account logins to the list
        if (personType == PersonType.EMPLOYEE) {
            loginList = Bank.clients
                    .stream()
                    .map(ClientImpl::getLogin)
                    .toList();
            personData[3] = PersonType.CLIENT.toString();
        } else if (personType == PersonType.ADMIN) {
            System.out.println("1 - Dodaj pracownika");
            System.out.println("2 - Dodaj admina");

            option = Data.getInt();

            switch (option) {
                case 1 -> {
                    loginList = Bank.employees
                            .stream()
                            .map(EmployeeImpl::getLogin)
                            .toList();
                    personData[3] = PersonType.EMPLOYEE.toString();
                }
                case 2 -> {
                    loginList = Bank.admins
                            .stream()
                            .map(AdminImpl::getLogin)
                            .toList();
                    personData[3] = PersonType.ADMIN.toString();
                }
                default -> throw new IllegalStateException("Incorrect option");
            }

        }

        //We take first and last name
        personData[0] = Data.getName();
        personData[1] = Data.getSurname();


        //We download the login and check if the login is not already taken
        do {
            personData[2] = Data.getLogin("Login: ");
            isLogin = loginList.contains(personData[2]);

            if (isLogin) {
                System.out.println("Login jest zajety");
            }
        } while (isLogin);


        return personData;
    }

    //Password generation
    public static String generatePassword() {
        var passwordLength = 10;
        var characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()_+";
        var random = new Random();
        var passwordBuilder = new StringBuilder();

        for (int i = 0; i < passwordLength; i++) {
            var randomChar = characters.charAt(random.nextInt(characters.length()));
            passwordBuilder.append(randomChar);
        }

        return passwordBuilder.toString();
    }

    public static String getNumberAccount(String context) {
        String number;
        do {
            System.out.print(context);
            number = scanner.nextLine();

            if (!Validator.isAccountNumber(number)) {
                System.out.println("Niepoprawny numer konta");
            }
        } while (!Validator.isAccountNumber(number));

        return number;
    }

    public static String getLogin(String context) {
        String login;
        do {
            System.out.print(context);
            login = scanner.nextLine();

            if (!Validator.loginValidator(login)) {
                System.out.println("Nieprawidlowy login");
            }
        } while (!Validator.loginValidator(login));

        return login;
    }

    public static String getPassword(String context) {
        String password;
        do {
            System.out.print(context);
            password = scanner.nextLine();

            if (!Validator.passwordValidator(password)) {
                System.out.println("Nieprawidlowe haslo");
            }
        } while (!Validator.passwordValidator(password));

        return password;
    }

    public static String getName() {
        String name;
        do {
            System.out.print("Podaj imie: ");
            name = scanner.nextLine();

            if (!Validator.nameValidator(name)) {
                System.out.println("Nieprawidlowe imie");
            }
        } while (!Validator.nameValidator(name));

        return name;
    }

    public static String getSurname() {
        String surname;
        do {
            System.out.print("Podaj nazwisko: ");
            surname = scanner.nextLine();

            if (!Validator.surnameValidator(surname)) {
                System.out.println("Nieprawidlowe nazwisko");
            }
        } while (!Validator.surnameValidator(surname));

        return surname;
    }

    public static String getText(String context) {
        System.out.print(context);
        return scanner.nextLine();
    }
}
