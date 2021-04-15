package com.challenge.ntest.domain.services;

import com.challenge.ntest.domain.exceptions.ValidationException;
import com.challenge.ntest.domain.models.*;

import java.util.Optional;

import static com.challenge.ntest.domain.validation.TransactionValidation.transactionValidation;

public class TransactionService {

    public AccountState process(StateHistory history, Transaction transaction) {
        Violations violations = new Violations();

        Account account = history.getCurrentAccount();

        try {
            transactionValidation(history, transaction, violations);
            return history.add(new Account(true, getSpare(history, transaction)), violations, transaction);
        } catch (ValidationException e) {
            return history
                    .add(Optional.ofNullable(account).map(Account::clone).orElse(null), violations, transaction);
        }

    }

    private long getSpare(StateHistory history, Transaction transaction){
        return history.getCurrentAccount().getAvailableLimit() - transaction.getAmount();
    }

}
