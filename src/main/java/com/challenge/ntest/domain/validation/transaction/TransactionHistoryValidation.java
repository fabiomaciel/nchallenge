package com.challenge.ntest.domain.validation.transaction;

import com.challenge.ntest.domain.models.*;

import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Objects;

public class TransactionHistoryValidation {

    private static final long TWO_MINUTES = 120;

    public static boolean historyValidation(StateHistory history, Transaction transaction, Violations violations) {
        LinkedList<Transaction> last2 = getLastTransactions(history);
        boolean lowFrequencyValid = highFrequencyValidation(transaction, violations, last2);
        boolean singleTransactionValid = doubleTransactionValidation(transaction, violations, last2);
        return lowFrequencyValid && singleTransactionValid;
    }

    private static boolean doubleTransactionValidation(Transaction transaction, Violations violations, LinkedList<Transaction> last2) {
        if (last2.size() >= 1) {
            Transaction lastTransaction = last2.getFirst();
            long diff = ChronoUnit.SECONDS.between(lastTransaction.getTime(), transaction.getTime());
            boolean sameMerchant = transaction.getMerchant().equals(lastTransaction.getMerchant());
            boolean sameAmount = transaction.getAmount() == lastTransaction.getAmount();

            if (sameMerchant && sameAmount && diff <= TWO_MINUTES) {
                violations.add(ViolationType.DOUBLE_TRANSACTION);
                return false;
            }
        }
        return true;
    }

    private static boolean highFrequencyValidation(Transaction transaction, Violations violations, LinkedList<Transaction> last2) {
        if (last2.size() == 2) {
            long diff = ChronoUnit.SECONDS.between(last2.getLast().getTime(), transaction.getTime());
            if (diff <= TWO_MINUTES) {
                violations.add(ViolationType.HIGH_FREQUENCY_SMALL_INTERVAL);
                return false;
            }
        }
        return true;
    }

    private static LinkedList<Transaction> getLastTransactions(StateHistory history) {
        LinkedList<Transaction> last3 = new LinkedList<>();

        var iterator = history.descendingIterator();
        var interval = 0;

        while (iterator.hasNext() && interval < 2) {
            AccountState current = iterator.next();
            if (Objects.nonNull(current.getTransaction())) {
                last3.add(current.getTransaction());
                interval++;
            }
        }
        return last3;
    }

    private TransactionHistoryValidation() {
    }
}
