package com;



import java.math.BigDecimal;
import observer.LoggingTransactionObserver;
import java.nio.file.Path;

import model.Account;
import model.AccountType;
import model.Transaction;
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

        BigDecimal interest =
                interestService.calculateInterest(account1, 365);

      
        System.out.println("===== BANKING SYSTEM =====");

        System.out.println("Total accounts: "
                + bank.getAllAccounts().size());

        System.out.println("Total bank balance: ₹"
                + bank.getTotalBankBalance());

        System.out.println("Interest for ACC1001: ₹"
                + interest);

        System.out.println("\nHigh-value accounts:");

        bank.getHighValueAccounts(new BigDecimal("500.00"))
                .forEach(account ->
                        System.out.println(
                                account.getAccountNumber()
                                + " - ₹" + account.getBalance()));

        System.out.println("\nTransaction History:");

        for (Transaction transaction :
                bank.getTransactionHistory("ACC1001")) {

            System.out.println(
                    transaction.getType()
                    + " | ₹" + transaction.getAmount()
                    + " | " + transaction.getTimestamp());
        }

        
        try {
            FileAccountRepository repository =
                    new FileAccountRepository();

            Path filePath = Path.of("data", "accounts.txt");

            repository.saveAccounts(bank.getAllAccounts(), filePath);

            System.out.println("\nAccounts saved successfully.");

            System.out.println("Loaded accounts: "
                    + repository.loadAccounts(filePath).size());

        } catch (Exception e) {
            System.out.println("Persistence error: " + e.getMessage());
        }
    }
}
