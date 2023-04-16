package validator;

public class Validator {
    private static String onlyDegitsRegex = "\\d+";
    private static String positiveNumberRegex = "\\d+(\\.\\d{1,2})?";
    private static String onlyLetter = "[A-Za-z]+";
    private static int accountNumberLength = 6;
    private static String loginRegex = "[\\w\\.]+";
    private static String passwordRegex = "[\\w!@#$%^&*()+]+";
    private static String nameRegex = "^[A-ZŁ][a-złęóążźń]+(\\s[A-ZŁ][a-złęóążźń]+){0,1}$";
    private static String surnameRegex = "^[A-ZŁ][a-złęóążźń]+(-[A-ZŁ][a-złęóążźń]+){0,1}$";
    public static int isInt(String number) {
        if (number.matches(onlyDegitsRegex)) {
            return Integer.parseInt(number);
        }
        throw new IllegalStateException("Input is not integer");
    }

    public static boolean isAccountNumber(String number) {
        if (number.matches(onlyDegitsRegex) && number.length() == accountNumberLength) {
            return true;
        }
        return false;
    }


    public static boolean positiveBigDecimalValidator(String number) {
        return number.matches(positiveNumberRegex);
    }

    public static boolean loginValidator(String login) {
        return login.matches(loginRegex);
    }

    public static boolean passwordValidator(String password) {
        return password.matches(passwordRegex);
    }

    public static boolean nameValidator(String name) {
        return name.matches(nameRegex);
    }

    public static boolean surnameValidator(String surname) {
        return surname.matches(surnameRegex);
    }
}
