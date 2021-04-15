package com.challenge.ntest.domain.validation.transaction;

import com.challenge.ntest.domain.exceptions.ValidationException;
import com.challenge.ntest.domain.models.StateHistory;
import com.challenge.ntest.domain.models.Transaction;
import com.challenge.ntest.domain.models.ViolationType;
import com.challenge.ntest.domain.models.Violations;

import java.util.Objects;

public class TransactionAccountValidation {

    public static void accountValidation(StateHistory history, Transaction transaction, Violations violations) {
        blankAccountValidation(history, transaction, violations);
        cardLimitValidation(history, transaction, violations);
        cardStatusValidation(history, transaction, violations);
    }

    private static void blankAccountValidation(StateHistory history, Transaction transaction, Violations violations) {
        if (Objects.isNull(history.getCurrentAccount())) {
            violations.add(ViolationType.ACCOUNT_NOT_INITIALIZED);
            throw new ValidationException(ViolationType.ACCOUNT_NOT_INITIALIZED);
        }
    }

    private static long cardLimitValidation(StateHistory history, Transaction transaction, Violations violations) {
        long spare = history.getCurrentAccount().getAvailableLimit() - transaction.getAmount();
        if (spare < 0) {
            violations.add(ViolationType.INSUFFICIENT_LIMIT);
            throw new ValidationException(ViolationType.INSUFFICIENT_LIMIT);
        }
        return spare;
    }

    private static void cardStatusValidation(StateHistory history, Transaction transaction, Violations violations) {
        if (!history.getCurrentAccount().isActiveCard()) {
            violations.add(ViolationType.CARD_NOT_ACTIVE);
            throw new ValidationException(ViolationType.CARD_NOT_ACTIVE);
        }
    }

    private TransactionAccountValidation() {
    }
}
