package com.challenge.ntest.domain.models;

public class AccountState {

    private Account account;
    private Violations violations;

    public AccountState() {
        this.violations = new Violations();
    }

    public AccountState(Account account) {
        this(account, new Violations());
    }

    public AccountState(Account account, Violations violations) {
        this.violations = violations;
        this.account = account;
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

    public void addViolation(String violation) {
        this.violations.add(violation);
    }
}