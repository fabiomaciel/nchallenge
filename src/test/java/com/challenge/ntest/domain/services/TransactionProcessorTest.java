package com.challenge.ntest.domain.services;

import com.challenge.ntest.domain.models.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.challenge.ntest.domain.services.TestUtils.createHistory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TransactionProcessorTest {

    public static final Account ACTIVE_ACCOUNT = new Account(true, 100);
    public static final Account INACTIVE_ACCOUNT = new Account(false, 100);

    public static final LocalDateTime NOW =
            LocalDateTime.of(2021, 4, 11, 12, 30);

    @Test
    public void shouldReturnAccountStateWithoutErrors() {
        Transaction transaction = new Transaction("habib's", 20, NOW);

        AccountState state = TransactionProcessor.process(createHistory(ACTIVE_ACCOUNT), transaction, AccountState::new);

        assertThat(state.getViolations(), hasSize(0));
        assertThat(state.getAccount(), is(notNullValue()));
        assertThat(state.getAccount().getAvailableLimit(), is(80L));

    }

    @Test
    public void shouldViolateCardANotActive() {
        Transaction transaction = new Transaction("habib's", 20, NOW);

        AccountState state = TransactionProcessor
                .process(createHistory(INACTIVE_ACCOUNT), transaction, AccountState::new);

        assertThat(state.getViolations(), hasSize(1));
        assertThat(state.getViolations().getFirst(), is(ViolationType.CARD_NOT_ACTIVE.getDescription()));
        assertThat(state.getAccount(), is(notNullValue()));
        assertThat(state.getAccount().getAvailableLimit(), is(100L));
    }

    @Test
    public void shouldViolateInsufficientLimit() {
        Transaction transaction = new Transaction("habib's", 101, NOW);

        AccountState state = TransactionProcessor.process(createHistory(ACTIVE_ACCOUNT), transaction, AccountState::new);

        assertThat(state.getViolations(), hasSize(1));
        assertThat(state.getViolations().getFirst(), is(ViolationType.INSUFFICIENT_LIMIT.getDescription()));
        assertThat(state.getAccount(), is(notNullValue()));
        assertThat(state.getAccount().getAvailableLimit(), is(100L));
    }

    @Test
    public void shouldViolateAccountNotInitialized() {
        Transaction transaction = new Transaction("habib's", 20, NOW);
        AccountState state = TransactionProcessor
                .process(createHistory(null), transaction, AccountState::new);

        assertThat(state.getViolations(), hasSize(1));
        assertThat(state.getViolations().getFirst(), is(ViolationType.ACCOUNT_NOT_INITIALIZED.getDescription()));
        assertThat(state.getAccount(), is(nullValue()));
    }
}
