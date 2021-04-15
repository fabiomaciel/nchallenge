package com.challenge.ntest.domain.services;

import com.challenge.ntest.domain.models.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static com.challenge.ntest.domain.services.TestUtils.createHistory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TransactionServiceTest {

    private static final TransactionService transactionService = new TransactionService();

    public static final Account ACTIVE_ACCOUNT = new Account(true, 100);
    public static final Account INACTIVE_ACCOUNT = new Account(false, 100);

    public static final LocalDateTime NOW =
            LocalDateTime.of(2021, 4, 11, 12, 30);

    @Test
    public void shouldReturnAccountStateWithoutErrors() {
        Transaction transaction = new Transaction("habib's", 20, NOW);

        StateHistory history = createHistory(ACTIVE_ACCOUNT);
        AccountState state = transactionService.process(history, transaction);

        assertThat(state.getViolations(), hasSize(0));
        assertThat(state.getAccount(), is(notNullValue()));
        assertThat(state.getAccount().getAvailableLimit(), is(80L));

        assertThat(history, hasSize(2));

    }

    @Test
    public void shouldViolateCardANotActive() {
        Transaction transaction = new Transaction("habib's", 20, NOW);

        StateHistory history = createHistory(INACTIVE_ACCOUNT);
        AccountState state = transactionService
                .process(history, transaction);

        assertThat(state.getViolations(), hasSize(1));
        assertThat(state.getViolations().getFirst(), is(ViolationType.CARD_NOT_ACTIVE.getDescription()));
        assertThat(state.getAccount(), is(notNullValue()));
        assertThat(state.getAccount().getAvailableLimit(), is(100L));

        assertThat(history, hasSize(2));
    }

    @Test
    public void shouldViolateInsufficientLimit() {
        Transaction transaction = new Transaction("habib's", 101, NOW);

        StateHistory history = createHistory(ACTIVE_ACCOUNT);
        AccountState state = transactionService.process(history, transaction);

        assertThat(state.getViolations(), hasSize(1));
        assertThat(state.getViolations().getFirst(), is(ViolationType.INSUFFICIENT_LIMIT.getDescription()));
        assertThat(state.getAccount(), is(notNullValue()));
        assertThat(state.getAccount().getAvailableLimit(), is(100L));

        assertThat(history, hasSize(2));
    }

    @Test
    public void shouldViolateAccountNotInitialized() {
        Transaction transaction = new Transaction("habib's", 20, NOW);
        StateHistory history = createHistory(null);
        AccountState state = transactionService
                .process(history, transaction);

        assertThat(state.getViolations(), hasSize(1));
        assertThat(state.getViolations().getFirst(), is(ViolationType.ACCOUNT_NOT_INITIALIZED.getDescription()));
        assertThat(state.getAccount(), is(nullValue()));

        assertThat(history, hasSize(1));
    }

    @Test
    public void shouldViolateHighFrequencySmallInterval() {
        var transactionList = Arrays.asList(
                new Transaction("habib's", 20, NOW),
                new Transaction("habib's 2", 20, NOW.plusSeconds(10)),
                new Transaction("habib's 3", 20, NOW.plusSeconds(20))
        );

        StateHistory history = createHistory(ACTIVE_ACCOUNT);

        transactionList.forEach(transaction -> {
            transactionService.process(history, transaction);
        });

        var state = history.getCurrent();

        assertThat(state.getViolations(), hasSize(1));
        assertThat(state.getViolations().getFirst(), is(ViolationType.HIGH_FREQUENCY_SMALL_INTERVAL.getDescription()));
        assertThat(state.getAccount(), is(notNullValue()));

        assertThat(history, hasSize(4));
    }

    @Test
    public void shouldViolateDoubleTransaction() {
        var transactionList = Arrays.asList(
                new Transaction("habib's", 20, NOW.plusSeconds(10)),
                new Transaction("habib's", 20, NOW.plusSeconds(20))
        );

        StateHistory history = createHistory(ACTIVE_ACCOUNT);

        transactionList.forEach(transaction -> transactionService.process(history, transaction));

        var state = history.getCurrent();

        assertThat(state.getViolations(), hasSize(1));
        assertThat(state.getViolations().getFirst(), is(ViolationType.DOUBLE_TRANSACTION.getDescription()));
        assertThat(state.getAccount(), is(notNullValue()));

        assertThat(history, hasSize(3));
    }

    @Test
    public void shouldViolateDoubleTransactionIfMerchantIsDifferent() {
        var transactionList = Arrays.asList(
                new Transaction("Mc Donalds", 20, NOW.minusHours(1)),
                new Transaction("habib's", 20, NOW.plusSeconds(10)),
                new Transaction("raggazzo", 20, NOW.plusSeconds(20))
        );

        StateHistory history = createHistory(ACTIVE_ACCOUNT);

        transactionList.forEach(transaction -> transactionService.process(history, transaction));

        var state = history.getCurrent();

        assertThat(state.getViolations(), hasSize(0));
        assertThat(state.getAccount(), is(notNullValue()));

        assertThat(history, hasSize(4));
    }

    @Test
    public void shouldViolateDoubleTransactionIfAmmountIsDifferent() {
        var transactionList = Arrays.asList(
                new Transaction("Mc Donalds", 20, NOW.minusHours(1)),
                new Transaction("raggazzo", 20, NOW.plusSeconds(10)),
                new Transaction("raggazzo", 30, NOW.plusSeconds(20))
        );

        StateHistory history = createHistory(ACTIVE_ACCOUNT);

        transactionList.forEach(transaction -> transactionService.process(history, transaction));

        var state = history.getCurrent();

        assertThat(state.getViolations(), hasSize(0));
        assertThat(state.getAccount(), is(notNullValue()));

        assertThat(history, hasSize(4));
    }

    @Test
    public void shouldViolateDoubleTransactionIfTimeDifferenceIsHigerThan2Minutos() {
        var transactionList = Arrays.asList(
                new Transaction("Mc Donalds", 20, NOW.minusHours(1)),
                new Transaction("raggazzo", 20, NOW),
                new Transaction("raggazzo", 20, NOW.plusSeconds(122))
        );

        StateHistory history = createHistory(ACTIVE_ACCOUNT);

        transactionList.forEach(transaction -> transactionService.process(history, transaction));

        var state = history.getCurrent();

        assertThat(state.getViolations(), hasSize(0));
        assertThat(state.getAccount(), is(notNullValue()));

        assertThat(history, hasSize(4));
    }
}
