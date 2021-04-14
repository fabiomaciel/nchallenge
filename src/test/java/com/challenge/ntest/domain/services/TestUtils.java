package com.challenge.ntest.domain.services;

import com.challenge.ntest.domain.models.Account;
import com.challenge.ntest.domain.models.AccountState;
import com.challenge.ntest.domain.models.StateHistory;
import com.challenge.ntest.domain.models.Violations;

public class TestUtils {

    public static StateHistory createHistory(Account account) {
        StateHistory history = new StateHistory();
        history.add(new AccountState(account, new Violations()));
        return history;
    }
}
