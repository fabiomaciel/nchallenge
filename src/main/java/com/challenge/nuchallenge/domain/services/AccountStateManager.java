package com.challenge.nuchallenge.domain.services;

import com.challenge.nuchallenge.domain.models.Account;
import com.challenge.nuchallenge.domain.models.AccountState;
import com.challenge.nuchallenge.domain.models.StateHistory;
import com.challenge.nuchallenge.domain.models.Transaction;
import java.util.List;

public class AccountStateManager {

    private final StateHistory history;
    private final AccountService accountService;
    private final TransactionService transactionService;

    public AccountStateManager() {
        this.history = new StateHistory();
        this.accountService = new AccountService();
        this.transactionService = new TransactionService();
    }

    public List<AccountState> getHistory() {
        return history;
    }

    public AccountState performOperation(Object operation) {
        if (operation instanceof Account) {
            return performOperation((Account) operation);
        }

        if (operation instanceof Transaction) {
            return performOperation((Transaction) operation);
        }

        return null;
    }

    public AccountState performOperation(Account account) {
        return accountService.process(history, account);
    }

    public AccountState performOperation(Transaction transaction) {
        return transactionService.process(history, transaction);
    }

}