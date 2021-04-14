package com.challenge.ntest.domain.services;

import com.challenge.ntest.domain.models.*;

import java.util.Objects;
import java.util.function.BiFunction;

public class TransactionProcessor {

    public static AccountState process(StateHistory history, Transaction transaction, BiFunction<Account, Violations, AccountState> fn) {
        Violations violations = new Violations();

        Account account = history.getCurrentAccount();

        if (Objects.isNull(account)) {
            violations.add(ViolationType.ACCOUNT_NOT_INITIALIZED);
            return fn.apply(null, violations);
        }

        if (!account.isActiveCard()) {
            violations.add(ViolationType.CARD_NOT_ACTIVE);
            return fn.apply(account, violations);
        }

        long spare = account.getAvailableLimit() - transaction.getAmount();
        if (spare < 0) {
            violations.add(ViolationType.INSUFFICIENT_LIMIT);
            return fn.apply(account, violations);
        } else {
            account = new Account(true, spare);
        }

        return fn.apply(account, violations);
    }

}
