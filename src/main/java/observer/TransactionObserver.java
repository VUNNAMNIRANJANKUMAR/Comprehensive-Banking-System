package observer;

import model.Transaction;

@FunctionalInterface
public interface TransactionObserver {

    void onTransaction(Transaction transaction);
}