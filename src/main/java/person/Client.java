package person;

import java.math.BigDecimal;

public interface Client {
    void bankTransfer();
    boolean isAccountNumber(String number);
    boolean isPayout(BigDecimal amount);
    void payment(BigDecimal amount);

}
