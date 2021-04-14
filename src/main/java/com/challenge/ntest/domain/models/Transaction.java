package com.challenge.ntest.domain.models;

import java.time.LocalDateTime;

public class Transaction {
    private String merchant;
    private long amount;
    private LocalDateTime time;

    public Transaction() {
    }

    public Transaction(String merchant, long amount, LocalDateTime time) {
        this.merchant = merchant;
        this.amount = amount;
        this.time = time;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
