
package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

import model.Account;
import model.AccountType;

public class FileAccountRepository {

    private static final Base64.Encoder ENCODER =
            Base64.getUrlEncoder();

    private static final Base64.Decoder DECODER =
            Base64.getUrlDecoder();

    public void saveAccounts(
            Collection<Account> accounts, Path filePath)
            throws IOException {

        Path parent = filePath.toAbsolutePath().getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }

        List<String> lines = new ArrayList<>();

        for (Account account : accounts) {

            String accountNumber = ENCODER.encodeToString(
                    account.getAccountNumber()
                            .getBytes(StandardCharsets.UTF_8));

            String accountHolder = ENCODER.encodeToString(
                    account.getAccountHolder()
                            .getBytes(StandardCharsets.UTF_8));

            String line = accountNumber + "|"
                    + accountHolder + "|"
                    + account.getAccountType().name() + "|"
                    + account.getBalance().toPlainString();

            lines.add(line);
        }

        Files.write(filePath, lines, StandardCharsets.UTF_8);
    }

    public List<Account> loadAccounts(Path filePath)
            throws IOException {

        List<Account> accounts = new ArrayList<>();

        if (!Files.exists(filePath)) {
            return accounts;
        }

        List<String> lines =
                Files.readAllLines(filePath, StandardCharsets.UTF_8);

        for (String line : lines) {

            if (line.isBlank()) {
                continue;
            }

            String[] data = line.split("\\|", -1);

            if (data.length != 4) {
                throw new IOException("Invalid account data: " + line);
            }

            try {
                String accountNumber = new String(
                        DECODER.decode(data[0]),
                        StandardCharsets.UTF_8);

                String accountHolder = new String(
                        DECODER.decode(data[1]),
                        StandardCharsets.UTF_8);

                AccountType accountType =
                        AccountType.valueOf(data[2]);

                Account account = new Account(
                        accountNumber,
                        accountHolder,
                        accountType,
                        new java.math.BigDecimal(data[3])
                );

                accounts.add(account);

            } catch (IllegalArgumentException e) {
                throw new IOException(
                        "Unable to read account data: " + line, e);
            }
        }

        return accounts;
    }
}
