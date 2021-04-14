package com.challenge.ntest.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {

    @JsonProperty("active-card")
    private boolean activeCard;

    @JsonProperty("available-limit")
    private long availableLimit;

    public Account() {
        this.activeCard = false;
        this.availableLimit = 0L;
    }

    public Account(long availableLimit) {
        this.activeCard = true;
        this.availableLimit = availableLimit;
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
}
