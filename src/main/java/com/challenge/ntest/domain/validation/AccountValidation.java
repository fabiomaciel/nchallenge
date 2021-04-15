package com.challenge.ntest.domain.validation;

import com.challenge.ntest.domain.exceptions.ValidationException;
import com.challenge.ntest.domain.models.StateHistory;
import com.challenge.ntest.domain.models.ViolationType;
import com.challenge.ntest.domain.models.Violations;

import java.util.Objects;

public class AccountValidation {

    public static void validate(StateHistory history, Violations violations){
        if (Objects.nonNull(history.getCurrentAccount())) {
            violations.add(ViolationType.ACCOUNT_ALREADY_INITIALIZED);
            throw new ValidationException(ViolationType.ACCOUNT_ALREADY_INITIALIZED);
        }
    }

}
