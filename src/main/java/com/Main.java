package com;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import model.Account;
import model.AccountType;
import model.Transaction;
import observer.LoggingTransactionObserver;
import persistence.FileAccountRepository;
import service.BankingService;
import strategy.InterestService;

public class Main {

    public static void main(String[] args) {

        BankingService bank = new BankingService();

        bank.addObserver(new LoggingTransactionObserver());

        Account account1 = bank.createAccount(
                "ACC1001", "Niranjan",
                AccountType.SAVINGS,
                new BigDecimal("1000.00"));

        Account account2 = bank.createAccount(
                "ACC1002", "Rahul",
                AccountType.CURRENT,
                new BigDecimal("500.00"));

        bank.deposit("ACC1001", new BigDecimal("500.00"));
        bank.withdraw("ACC1001", new BigDecimal("200.00"));

        bank.transfer(
                "ACC1001", "ACC1002",
                new BigDecimal("300.00"));

        InterestService interestService = new InterestService();

        System.out.println("\n======================================");
        System.out.println("       JAVA BANKING SYSTEM");
        System.out.println("======================================");

        System.out.println("\nSYSTEM OVERVIEW");
        System.out.println("--------------------------------------");

        System.out.println("Total Accounts: "
                + bank.getAllAccounts().size());

        System.out.println("Total Bank Balance: ₹"
                + bank.getTotalBankBalance());

        System.out.println("\nACCOUNT MANAGEMENT");
        System.out.println("--------------------------------------");

        for (AccountType type : AccountType.values()) {

            long count = bank.getAllAccounts().stream()
                    .filter(a -> a.getAccountType() == type)
                    .count();

            System.out.println(type + " Accounts: " + count);
        }

        System.out.println("\nHIGH-VALUE ACCOUNTS (> ₹500)");
        System.out.println("--------------------------------------");

        bank.getHighValueAccounts(new BigDecimal("500.00"))
                .forEach(account ->
                        System.out.println(
                                account.getAccountNumber()
                                + " - " + account.getAccountHolder()
                                + " - ₹" + account.getBalance()
                                + " (" + account.getAccountType() + ")"));

        System.out.println("\nTRANSACTION SUMMARY (TODAY)");
        System.out.println("--------------------------------------");

        Map<String, Integer> transactionCounts =
                new LinkedHashMap<>();

        Map<String, BigDecimal> transactionAmounts =
                new LinkedHashMap<>();

        int totalTransactions = 0;

        for (Account account : bank.getAllAccounts()) {

            for (Transaction transaction :
                    bank.getTransactionHistory(account.getAccountNumber())) {

                if (transaction.getTimestamp().toLocalDate()
                        .equals(LocalDate.now())) {

                    String type = String.valueOf(transaction.getType());

                    transactionCounts.merge(type, 1, Integer::sum);

                    transactionAmounts.merge(
                            type,
                            transaction.getAmount(),
                            BigDecimal::add);

                    totalTransactions++;
                }
            }
        }

        for (String type : transactionCounts.keySet()) {

            System.out.println(type + ": "
                    + transactionCounts.get(type)
                    + " transactions | ₹"
                    + transactionAmounts.get(type));
        }

        System.out.println("Total Transaction Records: "
                + totalTransactions);

        System.out.println("\nINTEREST CALCULATIONS");
        System.out.println("--------------------------------------");

        System.out.println("Savings Account: 4.0% p.a.");
        System.out.println("Current Account: 0.5% p.a.");
        System.out.println("Fixed Deposit: 6.5% p.a.");

        System.out.println("Interest for ACC1001 (365 days): ₹"
                + interestService.calculateInterest(account1, 365));

        System.out.println("\nJAVA FEATURES DEMONSTRATED");
        System.out.println("--------------------------------------");

        System.out.println("Collections Framework");
        System.out.println("Streams API and Lambda Expressions");
        System.out.println("Optional API");
        System.out.println("Date and Time API");
        System.out.println("BigDecimal for monetary precision");
        System.out.println("UUID for transaction identifiers");
        System.out.println("Java NIO for file persistence");

        System.out.println("\nDESIGN PATTERNS");
        System.out.println("--------------------------------------");

        System.out.println("1. Strategy Pattern");
        System.out.println("2. Factory Pattern");
        System.out.println("3. Singleton Pattern");
        System.out.println("4. Builder Pattern");
        System.out.println("5. Observer Pattern");

        System.out.println("\nFILE PERSISTENCE");
        System.out.println("--------------------------------------");

        try {

            FileAccountRepository repository =
                    new FileAccountRepository();

            Path filePath = Path.of("data", "accounts.txt");

            repository.saveAccounts(bank.getAllAccounts(), filePath);

            System.out.println("Accounts saved successfully.");

            System.out.println("Loaded accounts: "
                    + repository.loadAccounts(filePath).size());

        } catch (Exception e) {

            System.out.println("Persistence error: "
                    + e.getMessage());
        }

        System.out.println("\n======================================");
        System.out.println("       END OF BANKING REPORT");
        System.out.println("======================================");
    }
}
