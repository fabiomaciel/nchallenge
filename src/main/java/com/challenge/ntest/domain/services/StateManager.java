package com.challenge.ntest.domain.services;

import com.challenge.ntest.domain.models.Account;
import com.challenge.ntest.domain.models.AccountState;
import com.challenge.ntest.domain.models.StateHistory;
import com.challenge.ntest.domain.models.Transaction;

import java.util.List;
import java.util.Optional;

public class StateManager {

    private final StateHistory history;

    public StateManager() {
        this.history = new StateHistory();
    }

    public List<AccountState> getHistory() {
        return history;
    }

    public AccountState performOperation(Object operation) {
        if (operation instanceof Account) {
            return performOperation((Account) operation);
        }

        if (operation instanceof Transaction) {
            return performOperation((Transaction) operation);
        }

        return null;
    }


    public AccountState performOperation(Account account) {
        return AccountProcessor.process(
                history,
                account,
                (current, violations) -> addState(new AccountState(current, violations))
        );
    }

    public AccountState performOperation(Transaction transaction) {
        return TransactionProcessor.process(
                history,
                transaction,
                (account, violations) -> addState(new AccountState(account, violations))
        );
    }

    public AccountState addState(AccountState state) {
        history.add(state);
        return state;
    }


}