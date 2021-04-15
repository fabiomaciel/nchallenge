package com.challenge.ntest.domain.validation.transaction;

import com.challenge.ntest.domain.exceptions.ValidationException;
import com.challenge.ntest.domain.models.StateHistory;
import com.challenge.ntest.domain.models.Transaction;
import com.challenge.ntest.domain.models.ViolationType;
import com.challenge.ntest.domain.models.Violations;

import java.util.Objects;

public class TransactionAccountValidation {

    public static boolean accountValidation(StateHistory history, Transaction transaction, Violations violations) {
        blankAccountValidation(history, violations);
        cardStatusValidation(history, violations);
        return cardLimitValidation(history, transaction, violations);
    }

    private static void blankAccountValidation(StateHistory history, Violations violations) {
        if (Objects.isNull(history.getCurrentAccount())) {
            violations.add(ViolationType.ACCOUNT_NOT_INITIALIZED);
            throw new ValidationException(ViolationType.ACCOUNT_NOT_INITIALIZED);
        }
    }

    private static boolean cardLimitValidation(StateHistory history, Transaction transaction, Violations violations) {
        long spare = history.getCurrentAccount().getAvailableLimit() - transaction.getAmount();
        if (spare < 0) {
            violations.add(ViolationType.INSUFFICIENT_LIMIT);
            return false;
        }
        return true;
    }

    private static void cardStatusValidation(StateHistory history, Violations violations) {
        if (!history.getCurrentAccount().isActiveCard()) {
            violations.add(ViolationType.CARD_NOT_ACTIVE);
            throw new ValidationException(ViolationType.CARD_NOT_ACTIVE);
        }
    }

    private TransactionAccountValidation() {
    }
}
