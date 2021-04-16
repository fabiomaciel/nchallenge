package com.challenge.nuchallenge.domain.validation.transaction;

import com.challenge.nuchallenge.domain.models.*;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.BiPredicate;

public class TransactionHistoryValidation {

    private static final long TWO_MINUTES = 120;

    public static boolean historyValidation(StateHistory history, Transaction transaction, Violations violations) {
        boolean lowFrequencyValid = highFrequencyValidation(transaction, violations, history);
        boolean singleTransactionValid = doubleTransactionValidation(transaction, violations, history);
        return lowFrequencyValid && singleTransactionValid;
    }

    private static boolean doubleTransactionValidation(
            Transaction transaction,
            Violations violations,
            StateHistory history) {

        LinkedList<Transaction> last2Minute = getLastTransactions(history,
                (state, interval) ->
                     ChronoUnit.SECONDS
                             .between(state.getTransaction().getTime(), transaction.getTime()) <= TWO_MINUTES);

        for (Transaction pastTransaction : last2Minute) {
            boolean sameMerchant = transaction.getMerchant().equals(pastTransaction.getMerchant());
            boolean sameAmount = transaction.getAmount() == pastTransaction.getAmount();
            if (sameMerchant && sameAmount) {
                violations.add(ViolationType.DOUBLE_TRANSACTION);
                return false;
            }
        }
        return true;
    }

    private static boolean highFrequencyValidation(Transaction transaction, Violations violations, StateHistory history) {
        LinkedList<Transaction> last2 = getLastTransactions(history, (state, interval) -> interval < 2);
        if (last2.size() == 2) {
            long diff = ChronoUnit.SECONDS.between(last2.getLast().getTime(), transaction.getTime());
            if (diff <= TWO_MINUTES) {
                violations.add(ViolationType.HIGH_FREQUENCY_SMALL_INTERVAL);
                return false;
            }
        }
        return true;
    }

    private static LinkedList<Transaction> getLastTransactions(StateHistory history, BiPredicate<AccountState, Integer> predicate) {
        LinkedList<Transaction> list = new LinkedList<>();

        var iterator = history.descendingIterator();
        var transactionIndex = 0;

        while (iterator.hasNext()) {
            AccountState currentState = iterator.next();
            if (Objects.isNull(currentState.getTransaction()))
                continue;

            if (!predicate.test(currentState, transactionIndex))
                break;

            list.add(currentState.getTransaction());
            transactionIndex++;

        }
        return list;
    }

    private TransactionHistoryValidation() {
    }
}
