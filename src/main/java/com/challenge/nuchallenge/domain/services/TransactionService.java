package com.challenge.nuchallenge.domain.services;

import com.challenge.nuchallenge.domain.exceptions.ValidationException;
import com.challenge.nuchallenge.domain.models.*;

import java.util.Optional;

import static com.challenge.nuchallenge.domain.validation.TransactionValidation.transactionValidation;

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

    private long getSpare(StateHistory history, Transaction transaction) {
        return history.getCurrentAccount().getAvailableLimit() - transaction.getAmount();
    }

}
