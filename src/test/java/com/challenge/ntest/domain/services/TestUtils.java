package com.challenge.ntest.domain.services;

import com.challenge.ntest.domain.models.Account;
import com.challenge.ntest.domain.models.AccountState;
import com.challenge.ntest.domain.models.StateHistory;
import com.challenge.ntest.domain.models.Violations;

import java.util.Objects;

public class TestUtils {

    public static StateHistory createHistory(Account account) {
        StateHistory history = new StateHistory();
        if(Objects.nonNull(account))
            history.add(new AccountState(account, new Violations(), null));
        return history;
    }
}
