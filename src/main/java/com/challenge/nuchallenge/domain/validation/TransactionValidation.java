package com.challenge.nuchallenge.domain.validation;

import com.challenge.nuchallenge.domain.exceptions.ValidationException;
import com.challenge.nuchallenge.domain.models.StateHistory;
import com.challenge.nuchallenge.domain.models.Transaction;
import com.challenge.nuchallenge.domain.models.Violations;

import static com.challenge.nuchallenge.domain.validation.transaction.TransactionAccountValidation.accountValidation;
import static com.challenge.nuchallenge.domain.validation.transaction.TransactionHistoryValidation.historyValidation;

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
