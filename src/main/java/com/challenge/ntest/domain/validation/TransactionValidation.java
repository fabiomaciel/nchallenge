package com.challenge.ntest.domain.validation;

import com.challenge.ntest.domain.exceptions.ValidationException;
import com.challenge.ntest.domain.models.StateHistory;
import com.challenge.ntest.domain.models.Transaction;
import com.challenge.ntest.domain.models.Violations;

import static com.challenge.ntest.domain.validation.transaction.TransactionAccountValidation.accountValidation;
import static com.challenge.ntest.domain.validation.transaction.TransactionHistoryValidation.historyValidation;

public class TransactionValidation {

    public static void transactionValidation(StateHistory history, Transaction transaction, Violations violations) {
        boolean accountValid = accountValidation(history, transaction, violations);
        boolean historyValid = historyValidation(history, transaction, violations);

        if (!accountValid || !historyValid) {
            throw new ValidationException();
        }
    }

    private TransactionValidation() {
    }
}
