package com.challenge.ntest.domain.models;

import java.util.LinkedList;
import java.util.Optional;

public class StateHistory extends LinkedList<AccountState>{

    public AccountState getCurrent(){
        if (this.isEmpty()) return null;
        return this.getLast();
    }

    public Account getCurrentAccount(){
        return Optional.ofNullable(getCurrent())
                .map(AccountState::getAccount)
                .orElse(null);
    }

    public AccountState add(Account account, Violations violations, Transaction transaction){
        final AccountState state = new AccountState(account, violations, transaction);
        add(state);
        return state;
    }

}
