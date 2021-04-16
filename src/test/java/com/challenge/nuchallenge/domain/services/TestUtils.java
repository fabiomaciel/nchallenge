package com.challenge.nuchallenge.domain.services;

import com.challenge.nuchallenge.domain.models.Account;
import com.challenge.nuchallenge.domain.models.AccountState;
import com.challenge.nuchallenge.domain.models.StateHistory;
import com.challenge.nuchallenge.domain.models.Violations;
import java.util.Objects;

public class TestUtils {

    public static StateHistory createHistory(Account account) {
        StateHistory history = new StateHistory();
        if(Objects.nonNull(account))
            history.add(new AccountState(account, new Violations(), null));
        return history;
    }
}
