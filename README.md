# Comprehensive Banking System Simulation

## 1. Project Overview

The Comprehensive Banking System Simulation is a Core Java-based application that simulates essential banking operations. It manages customer accounts, processes financial transactions, calculates interest based on account types and stores account information using file handling.

The project demonstrates object-oriented programming, Java 8+ features, design patterns, exception handling, unit testing and file persistence.

## 2. Features

- Create and manage bank accounts.
- Support Savings, Current and Fixed Deposit accounts.
- Deposit and withdraw money.
- Transfer money between accounts.
- View transaction history.
- Calculate interest based on account type.
- Search for accounts using account numbers.
- Filter and sort high-value accounts.
- Calculate total bank balance.
- Count accounts by account type.
- Save and load account details using file persistence.
- Log banking activities.
- Notify observers when transactions occur.

## 3. Technologies Used

- **Programming Language:** Java 21
- **Build Tool:** Maven
- **Testing Framework:** JUnit 5
- **IDE:** Eclipse
- **Collections:** HashMap, ArrayList and List
- **Java Features:** Streams, Lambda Expressions, Optional and Functional Interfaces
- **File Handling:** Java NIO
- **Date and Time:** LocalDateTime
- **Monetary Calculations:** BigDecimal

## 4. Project Structure

```text
Comprehensive-Banking-System/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── com/
│   │       │   └── Main.java
│   │       │
│   │       ├── model/
│   │       │   ├── Account.java
│   │       │   ├── AccountType.java
│   │       │   └── Transaction.java
│   │       │
│   │       ├── service/
│   │       │   ├── BankingService.java
│   │       │   └── InterestService.java
│   │       │
│   │       ├── strategy/
│   │       │   ├── InterestStrategy.java
│   │       │   ├── SavingsInterestStrategy.java
│   │       │   ├── CurrentInterestStrategy.java
│   │       │   └── FixedDepositInterestStrategy.java
│   │       │
│   │       ├── factory/
│   │       │   └── AccountFactory.java
│   │       │
│   │       ├── singleton/
│   │       │   └── BankingLogger.java
│   │       │
│   │       ├── observer/
│   │       │   ├── TransactionObserver.java
│   │       │   └── LoggingTransactionObserver.java
│   │       │
│   │       ├── persistence/
│   │       │   └── FileAccountRepository.java
│   │       │
│   │       └── exception/
│   │           ├── InvalidAccountException.java
│   │           └── InsufficientBalanceException.java
│   │
│   └── test/
│       └── java/
│           └── com/
│               ├── AccountTest.java
│               ├── BankServiceTest.java
│               ├── TransactionTest.java
│               ├── BankingServiceTransactionTest.java
│               ├── BankingServiceTransferTest.java
│               ├── InterestServiceTest.java
│               ├── FileAccountRepositoryTest.java
│               ├── BankingServiceReportTest.java
│               └── DesignPatternTest.java
│
├── docs/
│   ├── TECHNICAL_DOCUMENTATION.md
│   └── TESTING.md
│
├── data/
│   └── accounts.txt
│
├── pom.xml
└── README.md
```

## 5. Design Patterns Implemented

The application implements five design patterns:

### 1. Strategy Pattern
- Defines a common interest calculation interface.
- Provides separate strategies for Savings, Current and Fixed Deposit accounts.
- Allows interest calculation logic to be selected based on account type.

### 2. Factory Pattern
- Uses `AccountFactory` to centralize account creation.
- Separates object creation from banking operations.

### 3. Singleton Pattern
- Uses `BankingLogger` to provide a single shared logger instance.
- Prevents multiple instances of the application logger.

### 4. Builder Pattern
- Uses `Transaction.Builder` to construct transaction objects step by step.
- Improves readability and object creation.

### 5. Observer Pattern
- Uses `TransactionObserver` to notify registered observers about transactions.
- `LoggingTransactionObserver` receives transaction notifications and logs transaction details.

## 6. Interest Calculation

The system uses different annual interest rates for account types:

| Account Type | Annual Interest Rate |
|---|---:|
| Savings | 4% |
| Current | 0.5% |
| Fixed Deposit | 6.5% |

Interest is calculated using the following formula:

**Interest = Principal × Rate × Number of Days / 365**

## 7. File Persistence

The application stores account information in:

`data/accounts.txt`

The `FileAccountRepository` class uses Java NIO to save and load account details.

Base64 encoding is used for account numbers and account-holder names.

Transaction history is maintained in memory and is not persisted.

## 8. Testing

JUnit 5 is used to test the application's functionality.

The test cases cover:

- Account creation and validation.
- Deposit and withdrawal operations.
- Transaction recording.
- Money transfers.
- Interest calculations.
- Account filtering and reporting.
- File saving and loading.
- Design pattern functionality.

### Test Execution Results

| Metric | Result |
|---|---:|
| Total Test Cases | 31 |
| Passed | 31 |
| Failed | 0 |
| Errors | 0 |

Run the complete test suite using:

```bash
mvn clean test
```

## 9. How to Run the Project

### Prerequisites
- Java JDK 21 or later.
- Eclipse IDE or another Java IDE.
- Maven.

### Steps

1. Clone or download the repository.
2. Import the project into Eclipse as an existing Maven project.
3. Allow Maven to download the required dependencies.
4. Open `src/main/java/com/Main.java`.
5. Run `Main.java` as a Java application.
6. View the banking operations and transaction details in the console.

Account information is saved in the `data/accounts.txt` file.

## 10. Future Enhancements

The following features can be added in future versions:

- Loan management.
- Database integration using JDBC or Spring Data JPA.
- Transaction history persistence.
- User authentication and authorization.
- Graphical user interface.
- Enhanced reporting and performance monitoring.

## 11. Conclusion

The Comprehensive Banking System Simulation demonstrates practical Core Java development through account management, transaction processing, interest calculations, file persistence and unit testing.

It also demonstrates the use of five design patterns, Java Collections, Streams, Lambda Expressions and custom exception handling to build a modular and maintainable application.

