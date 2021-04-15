package com.challenge.nuchallenge.domain.models;

public enum ViolationType {
    ACCOUNT_ALREADY_INITIALIZED("account-already-initialized"),
    ACCOUNT_NOT_INITIALIZED("account-not-initialized"),
    CARD_NOT_ACTIVE("card-not-active"),
    INSUFFICIENT_LIMIT("insufficient-limit"),
    HIGH_FREQUENCY_SMALL_INTERVAL("high-frequency-small-interval"),
    DOUBLE_TRANSACTION("double-transaction");

    private final String description;

    ViolationType(String name) {
        this.description = name;
    }

    public String getDescription() {
        return description;
    }
}