package com;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.InvalidAccountException;
import model.Account;
import model.AccountType;
import service.BankingService;

public class BankServiceTest {
	private BankingService bankService;
	private Account account;
	
	@BeforeEach
	void setUp() {
		bankService = new BankingService();
		account = bankService.createAccount("ACC1001","Niranjan",AccountType.SAVINGS, new BigDecimal("1000.00"));
	}
	
	@Test
	void shouldCreateAccountSuccessfully() {
		assertEquals("ACC1001", account.getAccountNumber());
		assertEquals("Niranjan", account.getAccountHolder());
		assertEquals(AccountType.SAVINGS, account.getAccountType());
		assertEquals(0, new BigDecimal("1000.00").compareTo(account.getBalance()));
	}
	
	@Test
	void shouldRejectDuplicateAccountNumber() {
		assertThrows(IllegalArgumentException.class, () -> bankService.createAccount("ACC1001","Niranjan",AccountType.SAVINGS, new BigDecimal("1000.00")));
	}
	
	@Test
	void shouldThrowExceptionForInvalidAccount() {
		assertThrows(InvalidAccountException.class, () -> bankService.getAccount("ACC9999"));
	}
	
	@Test
	void shouldDepositMoneySuccessfully() {
	    bankService.deposit("ACC1001", new BigDecimal("500.00"));

	    assertEquals(
	        0,
	        new BigDecimal("1500.00").compareTo(account.getBalance())
	    );
	}
}
