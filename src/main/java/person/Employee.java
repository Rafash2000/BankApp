package person;

public interface Employee {
    void addAccount();
    void deleteAccount();
    void payment();
    void payout();
    boolean isLogin(String employeeLogin);
}
