package com.challenge.ntest.domain.models;

public enum ViolationType {
    ACCOUNT_ALREADY_INITIALIZED("account-already-initialized"),
    ACCOUNT_NOT_INITIALIZED("account-not-initialized"),
    CARD_NOT_ACTIVE("card-not-active"),
    INSUFFICIENT_LIMIT("insufficient-limit");

    private final String description;

    ViolationType(String name) {
        this.description = name;
    }

    public String getDescription() {
        return description;
    }
}