package com.challenge.ntest.domain.validation;

import com.challenge.ntest.domain.models.StateHistory;
import com.challenge.ntest.domain.models.Transaction;
import com.challenge.ntest.domain.models.Violations;

import static com.challenge.ntest.domain.validation.transaction.TransactionAccountValidation.accountValidation;
import static com.challenge.ntest.domain.validation.transaction.TransactionHistoryValidation.historyValidation;

public class TransactionValidation {

    public static void transactionValidation(StateHistory history, Transaction transaction, Violations violations) {
        accountValidation(history, transaction, violations);
        historyValidation(history, transaction, violations);
    }

    private TransactionValidation() {
    }
}
