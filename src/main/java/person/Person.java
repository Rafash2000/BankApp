package person;

import data.database.OracleDAO;
import data.Data;
import validator.Validator;

public abstract class Person {
    final int id;
    final String name;
    final String surname;
    final String login;
    String password;
    final PersonType type;

    public Person(int id, String name, String surname, String login, String password, PersonType type) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.type = type;
    }


    public boolean checkLogin(String log, String pass) {
        return login.equals(log) && password.equals(pass);
    }

    //User password changing function
    public void changePassword() {
        String pass, pass1, pass2;
        var permission = false;
        for (int i = 0; i < 3; i++) {
            pass = Data.getText("Haslo: ");

            //If the user entered the correct password, we change the permission to true to allow the password change
            if (pass.equals(password)) {
                permission = true;
                break;
            }
        }

        if (permission) {
            System.out.println("1 - Wygeneruj nowe haslo");
            System.out.println("2 - Wpisz nowe haslo");

            var option = Data.getInt();

            switch (option) {
                //Password generation
                case 1 -> {
                    password = Data.generatePassword();
                    OracleDAO.updatePassword(id, password, type);
                    System.out.println("Nowe haslo: " + password);
                }
                //The user enters a new password
                case 2 -> {
                    for (int i = 0; i < 3; i++) {
                        pass1 = Data.getText("Nowe haslo: ");
                        pass2 = Data.getText("Potwierdz haslo: ");
                        if (pass1.equals(pass2) && Validator.passwordValidator(pass1)) {
                            if (password.equals(pass1)) {
                                System.out.println("Nowe haslo musi byc inne niz stare haslo");
                            } else {
                                password = pass1;
                                OracleDAO.updatePassword(id, password, type);
                                System.out.println("Haslo zostalo zmienione");
                                break;
                            }
                        } else {
                            System.out.println("Hasla sa rozne lub nieprawidlowe");
                        }
                    }
                }
                default -> System.out.println("Brak wybranej opcji");
            }


        }
    }


    public String getLogin() {
        return login;
    }

    @Override
    public String toString() {
        return "\n" + type.toString() + "\n" + name + " " +  surname + "\n";
    }
}
