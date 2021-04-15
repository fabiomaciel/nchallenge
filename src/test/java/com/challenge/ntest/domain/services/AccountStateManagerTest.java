package com.challenge.ntest.domain.services;

import com.challenge.ntest.domain.models.Account;
import com.challenge.ntest.domain.models.AccountState;
import com.challenge.ntest.domain.models.Transaction;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;

public class AccountStateManagerTest {

    @Test
    public void shouldPerformAnAccountOperation() {
        AccountStateManager manager = Mockito.spy(new AccountStateManager());

        Object account = new Account(true, 100);
        AccountState state = manager.performOperation(account);

        Mockito.verify(manager, times(1)).performOperation(any(Account.class));
        Mockito.verify(manager, times(0)).performOperation(any(Transaction.class));
        assertThat(state, notNullValue());

        assertThat(manager.getHistory(), hasSize(1));
    }

    @Test
    public void shouldPerformAnTransactionOperation() {
        AccountStateManager manager = Mockito.spy(new AccountStateManager());

        Object transaction = new Transaction("AmazonPrime", 10, LocalDateTime.now());
        AccountState state = manager.performOperation(transaction);

        Mockito.verify(manager, times(1)).performOperation(any(Transaction.class));
        Mockito.verify(manager, times(0)).performOperation(any(Account.class));

        assertThat(state, notNullValue());
        assertThat(manager.getHistory(), hasSize(1));

    }

    @Test
    public void shouldDoNothingAndReturnNullIfOerationNotMatch() {
        AccountStateManager manager = Mockito.spy(new AccountStateManager());

        Object transaction = "";
        AccountState state = manager.performOperation(transaction);

        Mockito.verify(manager, times(0)).performOperation(any(Transaction.class));
        Mockito.verify(manager, times(0)).performOperation(any(Account.class));

        assertThat(state, nullValue());
        assertThat(manager.getHistory(), hasSize(0));
    }

}
