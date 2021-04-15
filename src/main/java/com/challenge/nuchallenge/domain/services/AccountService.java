package com.challenge.nuchallenge.domain.services;

import com.challenge.nuchallenge.domain.exceptions.ValidationException;
import com.challenge.nuchallenge.domain.models.Account;
import com.challenge.nuchallenge.domain.models.AccountState;
import com.challenge.nuchallenge.domain.models.StateHistory;
import com.challenge.nuchallenge.domain.models.Violations;
import com.challenge.nuchallenge.domain.validation.AccountValidation;

public class AccountService {

    public AccountState process(
            StateHistory history,
            Account transactionAccount
    ) {
        Violations violations = new Violations();

        try {
            AccountValidation.validate(history, violations);
            return history.add(transactionAccount, violations, null);
        } catch (ValidationException e) {
            //return history.add(transactionAccount, violations, null);
            return history.add(history.getCurrentAccount().clone(), violations, null);
        }


    }

}
