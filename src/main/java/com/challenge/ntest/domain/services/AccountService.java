package com.challenge.ntest.domain.services;

import com.challenge.ntest.domain.exceptions.ValidationException;
import com.challenge.ntest.domain.models.Account;
import com.challenge.ntest.domain.models.AccountState;
import com.challenge.ntest.domain.models.StateHistory;
import com.challenge.ntest.domain.models.Violations;
import com.challenge.ntest.domain.validation.AccountValidation;

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
