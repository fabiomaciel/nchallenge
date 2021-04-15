package com.challenge.ntest.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountState {

    private Account account;
    private Violations violations;

    @JsonIgnore
    private Transaction transaction;

    public AccountState(Account account, Violations violations, Transaction transaction) {
        this.violations = violations;
        this.account = account;
        this.transaction = transaction;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Violations getViolations() {
        return violations;
    }

    public void setViolations(Violations violations) {
        this.violations = violations;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}