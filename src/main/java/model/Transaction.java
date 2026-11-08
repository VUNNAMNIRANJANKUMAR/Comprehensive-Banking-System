package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {

    private final String transactionId;
    private final String accountNumber;
    private final String type;
    private final BigDecimal amount;
    private final LocalDateTime timestamp;

    public Transaction(String transactionId, String accountNumber,
                       String type, BigDecimal amount) {

        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalArgumentException("Transaction ID is required");
        }

        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number is required");
        }

        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Transaction type is required");
        }

        Objects.requireNonNull(amount, "Amount is required");

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

   

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String transactionId;
        private String accountNumber;
        private String type;
        private BigDecimal amount;

        public Builder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Builder accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Transaction build() {
            return new Transaction(
                    transactionId,
                    accountNumber,
                    type,
                    amount);
        }
    }
}