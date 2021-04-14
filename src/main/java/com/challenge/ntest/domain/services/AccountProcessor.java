package com.challenge.ntest.domain.services;

import com.challenge.ntest.domain.models.*;

import java.util.Objects;
import java.util.function.BiFunction;

public class AccountProcessor {

    public static AccountState process(
            StateHistory history,
            Account transactionAccount,
            BiFunction<Account, Violations, AccountState> fn
    ) {
        Violations violations = new Violations();

        if (Objects.nonNull(history.getCurrentAccount())) {
            violations.add(ViolationType.ACCOUNT_ALREADY_INITIALIZED);
            return fn.apply(history.getCurrentAccount(), violations);
        }

        return fn.apply(transactionAccount, violations);
    }

}
