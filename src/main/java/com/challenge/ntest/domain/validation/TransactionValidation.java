package com.challenge.ntest.domain.validation;

import com.challenge.ntest.domain.exceptions.ValidationException;
import com.challenge.ntest.domain.models.*;

import static com.challenge.ntest.domain.validation.transaction.TransactionHistoryValidation.historyValidation;
import static com.challenge.ntest.domain.validation.transaction.TransactionAccountValidation.accountValidation;

public class TransactionValidation {

    public static void transactionValidation(StateHistory history, Transaction transaction, Violations violations) {
        accountValidation(history, transaction, violations);
        historyValidation(history, transaction, violations);
    }




}
