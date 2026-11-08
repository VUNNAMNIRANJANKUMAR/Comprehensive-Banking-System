package service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import exception.InvalidAccountException;
import factory.AccountFactory;
import model.Account;
import model.AccountType;
import model.Transaction;
import observer.TransactionObserver;
import singleton.BankingLogger;

public class BankingService {

    private final Map<String, Account> accounts = new HashMap<>();
    private final List<Transaction> transactions = new ArrayList<>();
    private final List<TransactionObserver> observers = new ArrayList<>();

    private final BankingLogger logger = BankingLogger.getInstance();

    public Account createAccount(String accountNumber, String accountHolder,
            AccountType accountType, BigDecimal initialBalance) {

        if (accounts.containsKey(accountNumber)) {
            throw new IllegalArgumentException("Account already exists");
        }

        Account account = AccountFactory.createAccount(
                accountNumber, accountHolder, accountType, initialBalance);

        accounts.put(accountNumber, account);

        logger.info("Account created: " + accountNumber);

        return account;
    }

    public Account getAccount(String accountNumber) {
        Account account = accounts.get(accountNumber);

        if (account == null) {
            throw new InvalidAccountException(
                    "Account not found: " + accountNumber);
        }

        return account;
    }

    public Optional<Account> findAccount(String accountNumber) {
        return Optional.ofNullable(accounts.get(accountNumber));
    }

    public void deposit(String accountNumber, BigDecimal amount) {
        Account account = getAccount(accountNumber);
        account.deposit(amount);

        recordTransaction(accountNumber, "DEPOSIT", amount);
    }

    public void withdraw(String accountNumber, BigDecimal amount) {
        Account account = getAccount(accountNumber);
        account.withdraw(amount);

        recordTransaction(accountNumber, "WITHDRAWAL", amount);
    }

    public void transfer(String fromAccountNumber,
                         String toAccountNumber,
                         BigDecimal amount) {

        if (Objects.equals(fromAccountNumber, toAccountNumber)) {
            throw new IllegalArgumentException(
                    "Cannot transfer to the same account");
        }

        Account sender = getAccount(fromAccountNumber);
        Account receiver = getAccount(toAccountNumber);

        sender.withdraw(amount);

        try {
            receiver.deposit(amount);
        } catch (RuntimeException e) {
            sender.deposit(amount);
            throw e;
        }

        recordTransaction(fromAccountNumber, "TRANSFER_OUT", amount);
        recordTransaction(toAccountNumber, "TRANSFER_IN", amount);
    }

    private void recordTransaction(
            String accountNumber,
            String type,
            BigDecimal amount) {

        Transaction transaction = Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .accountNumber(accountNumber)
                .type(type)
                .amount(amount)
                .build();

        transactions.add(transaction);

        logger.info("Transaction recorded: " + type);

        for (TransactionObserver observer : observers) {
            observer.onTransaction(transaction);
        }
    }

    public void addObserver(TransactionObserver observer) {
        observers.add(Objects.requireNonNull(observer));
    }

    public void removeObserver(TransactionObserver observer) {
        observers.remove(observer);
    }

    public List<Transaction> getTransactionHistory(String accountNumber) {
        getAccount(accountNumber);

        List<Transaction> history = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getAccountNumber().equals(accountNumber)) {
                history.add(transaction);
            }
        }

        return Collections.unmodifiableList(history);
    }

    public Collection<Account> getAllAccounts() {
        return List.copyOf(accounts.values());
    }

    public List<Account> getHighValueAccounts(BigDecimal threshold) {
        Objects.requireNonNull(threshold, "Threshold is required");

        return accounts.values().stream()
                .filter(account ->
                        account.getBalance().compareTo(threshold) > 0)
                .sorted(Comparator.comparing(Account::getBalance).reversed())
                .collect(Collectors.toList());
    }

    public BigDecimal getTotalBankBalance() {
        return accounts.values().stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public long countAccountsByType(AccountType accountType) {
        return accounts.values().stream()
                .filter(account ->
                        account.getAccountType() == accountType)
                .count();
    }
}