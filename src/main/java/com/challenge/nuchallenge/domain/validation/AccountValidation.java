package com.challenge.nuchallenge.domain.validation;

import com.challenge.nuchallenge.domain.exceptions.ValidationException;
import com.challenge.nuchallenge.domain.models.StateHistory;
import com.challenge.nuchallenge.domain.models.ViolationType;
import com.challenge.nuchallenge.domain.models.Violations;

import java.util.Objects;

public class AccountValidation {

    public static void validate(StateHistory history, Violations violations) {
        if (Objects.nonNull(history.getCurrentAccount())) {
            violations.add(ViolationType.ACCOUNT_ALREADY_INITIALIZED);
            throw new ValidationException(ViolationType.ACCOUNT_ALREADY_INITIALIZED);
        }
    }

    private AccountValidation() {
    }
}
