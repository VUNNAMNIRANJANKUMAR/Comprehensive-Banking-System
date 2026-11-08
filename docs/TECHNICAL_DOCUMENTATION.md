# Technical Documentation
## Comprehensive Banking System Simulation

### 1. Architecture

The application follows a simple layered structure:

- **Model Layer:** Represents accounts, account types and transactions.
- **Service Layer:** Contains banking operations and interest calculation logic.
- **Strategy Layer:** Provides different interest calculation algorithms.
- **Factory Layer:** Centralizes account object creation.
- **Singleton Layer:** Provides a single shared logging instance.
- **Observer Layer:** Handles transaction notifications.
- **Persistence Layer:** Saves and loads account information from a text file.
- **Exception Layer:** Contains custom exceptions for banking errors.

### 2. Main Components

**Account**
- Stores account number, account holder, account type and balance.
- Supports deposit and withdrawal operations.
- Prevents invalid amounts and insufficient-balance withdrawals.

**Transaction**
- Stores transaction ID, account number, transaction type, amount and timestamp.
- Uses the Builder Pattern for step-by-step object creation.
- Maintains a record of account activities.

**BankingService**
- Creates and retrieves accounts.
- Performs deposits, withdrawals and transfers.
- Maintains transaction history.
- Provides account filtering and balance reports.
- Notifies registered observers when transactions occur.

**InterestService**
- Selects the appropriate interest strategy based on account type.
- Calculates interest for a specified number of days.

**AccountFactory**
- Creates account objects through a centralized factory method.
- Implements the Factory Pattern.

**BankingLogger**
- Provides a single shared logger instance.
- Implements the Singleton Pattern.

**TransactionObserver**
- Defines the notification mechanism for transaction events.
- Implemented by LoggingTransactionObserver.

**FileAccountRepository**
- Saves account information to a text file.
- Loads saved account information when requested.

### 3. Design Patterns Implemented

**1. Strategy Pattern**
- Defines a common interest calculation interface.
- Provides separate strategies for Savings, Current and Fixed Deposit accounts.
- Allows interest calculation logic to be selected dynamically.

**2. Factory Pattern**
- Uses AccountFactory to centralize account creation.
- Separates object creation from the banking service.

**3. Singleton Pattern**
- Uses BankingLogger to provide one shared logger instance.
- Prevents multiple instances of the application logger.

**4. Builder Pattern**
- Uses Transaction.Builder to construct transaction objects step by step.
- Improves readability when creating transaction objects.

**5. Observer Pattern**
- Uses TransactionObserver to notify registered observers about transactions.
- LoggingTransactionObserver receives transaction notifications and logs transaction details.

### 4. Java Concepts Used

- Encapsulation and object-oriented programming.
- Enums for account types.
- Collections: HashMap, ArrayList and List.
- Streams for filtering, sorting and aggregation.
- Lambda expressions for collection processing.
- Optional for handling potentially missing accounts.
- BigDecimal for monetary calculations.
- UUID for transaction ID generation.
- LocalDateTime for transaction timestamps.
- Exception handling and custom exceptions.
- File handling using Java NIO.
- Functional interfaces and method references.

### 5. Transaction Processing

**Deposit**
- Validates the amount.
- Updates the account balance.
- Records the transaction.
- Notifies registered observers.

**Withdrawal**
- Checks the available balance.
- Updates the account balance.
- Records the transaction.
- Notifies registered observers.

**Transfer**
- Validates both accounts.
- Withdraws money from the sender.
- Deposits money into the receiver.
- Records both transfer transactions.
- Notifies registered observers.

### 6. File Persistence

Account information is stored in `data/accounts.txt`.

The repository uses Java NIO file operations and Base64 encoding for account numbers and account-holder names.

Transaction history is maintained in memory and is not persisted.

### 7. Testing

JUnit 5 is used for unit testing.

The test cases cover:
- Account creation and validation.
- Deposit and withdrawal operations.
- Transaction recording.
- Money transfers.
- Interest calculations.
- Account filtering and reporting.
- File saving and loading.
- Design pattern functionality.

### 8. Limitations and Future Improvements

- Add loan management.
- Persist transaction history.
- Integrate a relational database.
- Add a user interface.
- Introduce authentication and authorization.
- Add further validation and performance monitoring.

### 9. Conclusion

This project demonstrates core Java programming, object-oriented design, collections, exception handling, Java 8+ features, five design patterns, unit testing and file persistence through a banking system simulation.