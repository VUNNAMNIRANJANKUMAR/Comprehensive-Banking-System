package com;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import factory.AccountFactory;
import model.Account;
import model.AccountType;
import model.Transaction;
import observer.TransactionObserver;
import service.BankingService;
import singleton.BankingLogger;

public class DesignPatternTest {

    @Test
    void shouldCreateAccountUsingFactory() {
        Account account = AccountFactory.createAccount(
                "ACC501", "Niranjan",
                AccountType.SAVINGS, new BigDecimal("1000"));

        assertEquals("ACC501", account.getAccountNumber());
    }

    @Test
    void shouldReturnSameSingletonInstance() {
        assertSame(
                BankingLogger.getInstance(),
                BankingLogger.getInstance());
    }

    @Test
    void shouldBuildTransaction() {
        Transaction transaction = Transaction.builder()
                .transactionId("TXN501")
                .accountNumber("ACC501")
                .type("DEPOSIT")
                .amount(new BigDecimal("500"))
                .build();

        assertEquals("DEPOSIT", transaction.getType());
        assertEquals(new BigDecimal("500"), transaction.getAmount());
    }

    @Test
    void shouldNotifyObserverAfterDeposit() {
        BankingService bank = new BankingService();

        bank.createAccount("ACC501", "Niranjan",
                AccountType.SAVINGS, new BigDecimal("1000"));

        AtomicInteger notifications = new AtomicInteger(0);

        TransactionObserver observer =
                transaction -> notifications.incrementAndGet();

        bank.addObserver(observer);

        bank.deposit("ACC501", new BigDecimal("200"));

        assertEquals(1, notifications.get());

        bank.removeObserver(observer);

        bank.deposit("ACC501", new BigDecimal("100"));

        assertEquals(1, notifications.get());
    }
}