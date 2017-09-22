package com.vivek.threading;

/**
 * Created by VJaiswal on 18/08/17.
 */
public class DeadlockDynamicLockOrdering {

    private final Object tieLock = new Object();

    public void deadlockProneTransferMoney(Account fromAccount, Account toAccount, int amount) {
        synchronized (fromAccount) {
            synchronized (toAccount) {
                fromAccount.debit(amount);
                toAccount.credit(amount);
            }
        }
    }

    public void deadlockFreeTransferMoney(Account fromAccount, Account toAccount, int amount) {
        int fromHash = System.identityHashCode(fromAccount);
        int toHash = System.identityHashCode(toAccount);

        class transferMoneyHelper {
            void transfer(Account fromAccount, Account toAccount, int amount) {
                fromAccount.debit(amount);
                toAccount.credit(amount);
            }
        }

        if (fromHash < toHash) {
            synchronized (fromAccount) {
                synchronized (toAccount) {
                    new transferMoneyHelper().transfer(fromAccount, toAccount, amount);
                }
            }
        } else if (fromHash < toHash) {
            synchronized (toAccount) {
                synchronized (fromAccount) {
                    new transferMoneyHelper().transfer(fromAccount, toAccount, amount);
                }
            }
        } else {
            synchronized (tieLock) {
                synchronized (fromAccount) {
                    synchronized (toAccount) {
                        new transferMoneyHelper().transfer(fromAccount, toAccount, amount);
                    }
                }
            }
        }
    }

    static class Account {
        int balance;

        int getBalance() {
            return balance;
        }

        void debit(int toDeduct) {
            balance = balance - toDeduct;
        }

        void credit(int toCredit) {
            balance = balance + toCredit;
        }
    }
}
