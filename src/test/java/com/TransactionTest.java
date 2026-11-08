package com;


import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import model.Transaction;

public class TransactionTest {

    @Test
    void shouldCreateTransactionSuccessfully() {

        Transaction transaction = new Transaction(
                "TXN1001",
                "ACC1001",
                "DEPOSIT",
                new BigDecimal("500.00")
        );

        assertEquals("TXN1001", transaction.getTransactionId());
        assertEquals("ACC1001", transaction.getAccountNumber());
        assertEquals("DEPOSIT", transaction.getType());

        assertEquals(
                0,
                new BigDecimal("500.00").compareTo(transaction.getAmount())
        );

        assertNotNull(transaction.getTimestamp());
    }

    @Test
    void shouldRejectZeroAmount() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Transaction(
                        "TXN1002",
                        "ACC1001",
                        "DEPOSIT",
                        BigDecimal.ZERO
                )
        );
    }

    @Test
    void shouldRecordTransactionTimestamp() {

        LocalDateTime before = LocalDateTime.now();

        Transaction transaction = new Transaction(
                "TXN1003",
                "ACC1001",
                "WITHDRAW",
                new BigDecimal("200.00")
        );

        LocalDateTime after = LocalDateTime.now();

        assertFalse(transaction.getTimestamp().isBefore(before));
        assertFalse(transaction.getTimestamp().isAfter(after));
    }
}
