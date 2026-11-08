package observer;

import model.Transaction;
import singleton.BankingLogger;

public class LoggingTransactionObserver implements TransactionObserver {

    @Override
    public void onTransaction(Transaction transaction) {

        BankingLogger.getInstance().info(
                "Transaction: " + transaction.getType()
                + " | Account: " + transaction.getAccountNumber()
                + " | Amount: " + transaction.getAmount());
    }
}