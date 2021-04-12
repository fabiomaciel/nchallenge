package com.challenge.ntest.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account{

    @JsonProperty("active-card")
    private boolean activeCard;

    @JsonProperty("available-limit")
    private long availableLimit;

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
