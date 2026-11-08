
package model;

import java.math.BigDecimal;
import java.util.Objects;

import exception.InsufficientBalanceException;

public class Account {

    private final String accountNumber;
    private final String accountHolder;
    private final AccountType accountType;
    private BigDecimal balance;

    public Account(String accountNumber, String accountHolder,
                   AccountType accountType, BigDecimal initialBalance) {

        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "Account number cannot be empty");
        }

        if (accountHolder == null || accountHolder.isBlank()) {
            throw new IllegalArgumentException(
                    "Account holder cannot be empty");
        }

        this.accountType = Objects.requireNonNull(
                accountType, "Account type is required");

        Objects.requireNonNull(initialBalance,
                "Initial balance is required");

        if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Initial balance cannot be negative");
        }

        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
	    }
	
	    public String getAccountNumber() {
	        return accountNumber;
	    }
	
	    public String getAccountHolder() {
	        return accountHolder;
	    }
	
	    public AccountType getAccountType() {
	        return accountType;
	    }
	
	    public BigDecimal getBalance() {
	        return balance;
	    }
	
	    public void deposit(BigDecimal amount) {
	        validateAmount(amount);
	        balance = balance.add(amount);
	    }
	
	    public void withdraw(BigDecimal amount) {
	        validateAmount(amount);
	
	        if (balance.compareTo(amount) < 0) {
	            throw new InsufficientBalanceException(
	                    "Insufficient account balance. Available balance : "+balance);
	        }
	
	        balance = balance.subtract(amount);
	    }
	
	    private void validateAmount(BigDecimal amount) {
	        Objects.requireNonNull(amount, "Amount is required");
	
	        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
	            throw new IllegalArgumentException(
	                    "Amount must be greater than zero");
	        }
	    }
}
