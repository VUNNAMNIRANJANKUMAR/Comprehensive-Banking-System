
package com;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.AccountType;
import model.Transaction;
import service.BankingService;

public class BankingServiceTransactionTest {

    private BankingService bankService;

    @BeforeEach
    void setUp() {
        bankService = new BankingService();

        bankService.createAccount(
                "ACC1001",
                "Niranjan",
                AccountType.SAVINGS,
                new BigDecimal("1000.00")
        );
    }

    @Test
    void shouldRecordDepositTransaction() {
        bankService.deposit("ACC1001", new BigDecimal("500.00"));

        List<Transaction> history =
                bankService.getTransactionHistory("ACC1001");

        assertEquals(1, history.size());
        assertEquals("DEPOSIT", history.get(0).getType());
        assertEquals(
                0,
                new BigDecimal("500.00").compareTo(history.get(0).getAmount())
        );
    }

    @Test
    void shouldRecordWithdrawalTransaction() {
        bankService.withdraw("ACC1001", new BigDecimal("200.00"));

        List<Transaction> history =
                bankService.getTransactionHistory("ACC1001");

        assertEquals(1, history.size());
        assertEquals("WITHDRAWAL", history.get(0).getType());
        assertEquals(
                0,
                new BigDecimal("200.00").compareTo(history.get(0).getAmount())
        );
    }

    @Test
    void shouldRecordMultipleTransactions() {
        bankService.deposit("ACC1001", new BigDecimal("500.00"));
        bankService.withdraw("ACC1001", new BigDecimal("200.00"));

        List<Transaction> history =
                bankService.getTransactionHistory("ACC1001");

        assertEquals(2, history.size());
    }
}
