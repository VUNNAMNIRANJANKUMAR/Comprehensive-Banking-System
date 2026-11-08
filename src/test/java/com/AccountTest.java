package com;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.InsufficientBalanceException;
import model.Account;
import model.AccountType;

public class AccountTest {
	private Account account;
	
	@BeforeEach
	void setUp() {
		account = new Account("ACC1001","Niranjan",AccountType.SAVINGS,new BigDecimal("1000.00"));
	}
	
	@Test
	void shouldDepositMoney() {
		account.deposit(new BigDecimal("500.00"));
		
		assertEquals(0, new BigDecimal("1500.00").compareTo(account.getBalance()));
		
	}
	
	@Test
    void shouldWithdrawMoney() {
        account.withdraw(new BigDecimal("200.00"));

        assertEquals(0,new BigDecimal("800.00").compareTo(account.getBalance()));
    }

    @Test
    void shouldRejectWithdrawalWithInsufficientBalance() {
        assertThrows(InsufficientBalanceException.class,() -> account.withdraw(new BigDecimal("2000.00")));

        assertEquals(0,new BigDecimal("1000.00").compareTo(account.getBalance()));
    }

    @Test
    void shouldRejectZeroDeposit() {
        assertThrows(IllegalArgumentException.class,() -> account.deposit(BigDecimal.ZERO)
        );
    }
}
