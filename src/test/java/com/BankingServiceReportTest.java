package com;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Account;
import model.AccountType;
import service.BankingService;

public class BankingServiceReportTest {

    private BankingService bank;

    @BeforeEach
    void setUp() {
        bank = new BankingService();

        bank.createAccount("ACC101", "Niranjan",
                AccountType.SAVINGS, new BigDecimal("1000"));

        bank.createAccount("ACC102", "Rahul",
                AccountType.CURRENT, new BigDecimal("500"));

        bank.createAccount("ACC103", "Kumar",
                AccountType.SAVINGS, new BigDecimal("2000"));
    }

    @Test
    void shouldCalculateTotalBankBalance() {
        assertEquals(0, new BigDecimal("3500.00").compareTo(
                bank.getTotalBankBalance()));
    }

    @Test
    void shouldReturnHighValueAccountsInDescendingOrder() {
        List<Account> accounts =
                bank.getHighValueAccounts(new BigDecimal("700"));

        assertEquals(2, accounts.size());
        assertEquals("ACC103", accounts.get(0).getAccountNumber());
        assertEquals("ACC101", accounts.get(1).getAccountNumber());
    }

    @Test
    void shouldCountAccountsByType() {
        assertEquals(2, bank.countAccountsByType(AccountType.SAVINGS));
        assertEquals(1, bank.countAccountsByType(AccountType.CURRENT));
    }

    @Test
    void shouldFindExistingAccount() {
        assertTrue(bank.findAccount("ACC101").isPresent());
    }

    @Test
    void shouldReturnEmptyForMissingAccount() {
        assertTrue(bank.findAccount("ACC999").isEmpty());
    }
}