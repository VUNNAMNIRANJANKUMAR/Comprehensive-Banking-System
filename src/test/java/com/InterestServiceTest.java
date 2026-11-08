package com;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import model.Account;
import model.AccountType;
import strategy.InterestService;

public class InterestServiceTest {

    private final InterestService interestService = new InterestService();

    @Test
    void shouldCalculateSavingsInterest() {
        Account account = new Account(
                "ACC101", "Niranjan",
                AccountType.SAVINGS, new BigDecimal("1000"));

        BigDecimal interest = interestService.calculateInterest(account, 365);

        assertEquals(new BigDecimal("40.00"), interest);
    }

    @Test
    void shouldCalculateFixedDepositInterest() {
        Account account = new Account(
                "ACC102", "Rahul",
                AccountType.FIXED_DEPOSIT, new BigDecimal("1000"));

        BigDecimal interest = interestService.calculateInterest(account, 365);

        assertEquals(new BigDecimal("65.00"), interest);
    }

    @Test
    void shouldRejectNegativeDays() {
        Account account = new Account(
                "ACC103", "Kumar",
                AccountType.SAVINGS, new BigDecimal("1000"));

        assertThrows(IllegalArgumentException.class,
                () -> interestService.calculateInterest(account, -1));
    }
}