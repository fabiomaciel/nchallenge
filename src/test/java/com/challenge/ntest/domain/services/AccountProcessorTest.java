package com.challenge.ntest.domain.services;

import com.challenge.ntest.domain.models.*;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import static com.challenge.ntest.domain.services.TestUtils.createHistory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class AccountProcessorTest {

    public static final Account ACTIVE_ACCOUNT = new Account(true, 100);

    @Test
    public void shouldReturnAccountStateWithoutErrors(){

        StateHistory history = createHistory(null);
        AccountState state = AccountProcessor.process(history, ACTIVE_ACCOUNT);

        assertThat(state.getAccount(), Is.is(ACTIVE_ACCOUNT));
        assertThat(state.getViolations(), hasSize(0));
        assertThat(history, hasSize(1));
    }

    @Test
    public void shouldViolateCardAlreadyActive(){
        Account account = new Account(true, 200);
        StateHistory history = createHistory(ACTIVE_ACCOUNT);
        AccountState state = AccountProcessor.process(history, account);

        assertThat(state.getAccount(), Is.is(ACTIVE_ACCOUNT));
        assertThat(state.getViolations(), hasSize(1));
        assertThat(state.getViolations().getFirst(), is(ViolationType.ACCOUNT_ALREADY_INITIALIZED.getDescription()));
        assertThat(history, hasSize(2));
    }

}
