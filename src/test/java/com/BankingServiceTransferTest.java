
package com;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.AccountType;
import service.BankingService;

public class BankingServiceTransferTest {

    private BankingService bankService;

    @BeforeEach
    void setUp() {
        bankService = new BankingService();

        bankService.createAccount(
                "ACC1001", "Niranjan",
                AccountType.SAVINGS, new BigDecimal("1000.00"));

        bankService.createAccount(
                "ACC1002", "Rahul",
                AccountType.SAVINGS, new BigDecimal("500.00"));
    }

    @Test
    void shouldTransferMoneySuccessfully() {

        bankService.transfer(
                "ACC1001", "ACC1002", new BigDecimal("300.00"));

        assertEquals(
                0,
                new BigDecimal("700.00").compareTo(
                        bankService.getAccount("ACC1001").getBalance()));

        assertEquals(
                0,
                new BigDecimal("800.00").compareTo(
                        bankService.getAccount("ACC1002").getBalance()));
    }

    @Test
    void shouldRecordTransferTransactions() {

        bankService.transfer(
                "ACC1001", "ACC1002", new BigDecimal("300.00"));

        assertEquals(1,
                bankService.getTransactionHistory("ACC1001").size());

        assertEquals(1,
                bankService.getTransactionHistory("ACC1002").size());

        assertEquals("TRANSFER_OUT",
                bankService.getTransactionHistory("ACC1001")
                        .get(0).getType());

        assertEquals("TRANSFER_IN",
                bankService.getTransactionHistory("ACC1002")
                        .get(0).getType());
    }

    @Test
    void shouldRejectTransferWithInsufficientBalance() {

        assertThrows(
                RuntimeException.class,
                () -> bankService.transfer(
                        "ACC1001", "ACC1002",
                        new BigDecimal("2000.00"))
        );

        assertEquals(
                0,
                new BigDecimal("1000.00").compareTo(
                        bankService.getAccount("ACC1001").getBalance()));
    }
}
