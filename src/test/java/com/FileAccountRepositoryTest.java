package com;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import model.Account;
import model.AccountType;
import persistence.FileAccountRepository;

public class FileAccountRepositoryTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldSaveAndLoadAccounts() throws Exception {
        FileAccountRepository repository = new FileAccountRepository();

        Account account = new Account(
                "ACC101", "Niranjan Kumar",
                AccountType.SAVINGS, new BigDecimal("1500.00"));

        Path filePath = tempDir.resolve("accounts.txt");

        repository.saveAccounts(List.of(account), filePath);

        List<Account> loadedAccounts = repository.loadAccounts(filePath);

        assertEquals(1, loadedAccounts.size());
        assertEquals("ACC101", loadedAccounts.get(0).getAccountNumber());
        assertEquals("Niranjan Kumar", loadedAccounts.get(0).getAccountHolder());
        assertEquals(AccountType.SAVINGS, loadedAccounts.get(0).getAccountType());
        assertEquals(new BigDecimal("1500.00"), loadedAccounts.get(0).getBalance());
    }

    @Test
    void shouldReturnEmptyListWhenFileDoesNotExist() throws Exception {
        FileAccountRepository repository = new FileAccountRepository();

        Path filePath = tempDir.resolve("missing.txt");

        assertTrue(repository.loadAccounts(filePath).isEmpty());
    }
}