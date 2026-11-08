package factory;

import java.math.BigDecimal;

import model.Account;
import model.AccountType;

public class AccountFactory {

    public static Account createAccount(
            String accountNumber,
            String accountHolder,
            AccountType accountType,
            BigDecimal initialBalance) {

        return new Account(
                accountNumber,
                accountHolder,
                accountType,
                initialBalance);
    }
}