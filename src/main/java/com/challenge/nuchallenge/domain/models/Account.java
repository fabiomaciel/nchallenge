package com.challenge.nuchallenge.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account implements Cloneable {

    @JsonProperty("active-card")
    private boolean activeCard;

    @JsonProperty("available-limit")
    private long availableLimit;

    public Account() {
    }

    public Account(boolean activeCard, long availableLimit) {
        this.activeCard = activeCard;
        this.availableLimit = availableLimit;
    }

    public boolean isActiveCard() {
        return activeCard;
    }

    public void setActiveCard(boolean activeCard) {
        this.activeCard = activeCard;
    }

    public long getAvailableLimit() {
        return availableLimit;
    }

    public void setAvailableLimit(long availableLimit) {
        this.availableLimit = availableLimit;
    }

    @Override
    public Account clone() {
        return new Account(this.activeCard, this.availableLimit);
    }
}
