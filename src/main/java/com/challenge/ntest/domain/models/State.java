package com.challenge.ntest.domain.models;

import java.util.LinkedList;
import java.util.List;

public class State {

    Account account;
    List<Transaction> transactions;

    public State() {
        this.account = null;
        this.transactions = new LinkedList<>();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
