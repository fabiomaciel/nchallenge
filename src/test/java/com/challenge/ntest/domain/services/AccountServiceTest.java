package com.challenge.ntest.domain.services;

import com.challenge.ntest.domain.models.Account;
import com.challenge.ntest.domain.models.AccountState;
import com.challenge.ntest.domain.models.StateHistory;
import com.challenge.ntest.domain.models.ViolationType;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;

import static com.challenge.ntest.domain.services.TestUtils.createHistory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class AccountServiceTest {

    private static final AccountService accountService = new AccountService();

    private static final Account ACTIVE_ACCOUNT = new Account(true, 100);

    @Test
    public void shouldReturnAccountStateWithoutErrors() {

        StateHistory history = createHistory(null);
        AccountState state = accountService.process(history, ACTIVE_ACCOUNT);

        assertThat(state.getAccount(), Is.is(ACTIVE_ACCOUNT));
        assertThat(state.getViolations(), hasSize(0));
        assertThat(history, hasSize(1));
    }

    @Test
    public void shouldViolateCardAlreadyActive() {
        Account account = new Account(true, 200);
        StateHistory history = createHistory(ACTIVE_ACCOUNT);
        AccountState state = accountService.process(history, account);

        Account currentAccount = history.getCurrentAccount();

        assertThat(state.getAccount(), IsNot.not(ACTIVE_ACCOUNT));
        assertThat(state.getAccount(), IsNot.not(account));
        assertThat(state.getViolations(), hasSize(1));
        assertThat(state.getViolations().getFirst(), is(ViolationType.ACCOUNT_ALREADY_INITIALIZED.getDescription()));
        assertThat(history, hasSize(2));

        assertThat(currentAccount.getAvailableLimit(), Is.is(ACTIVE_ACCOUNT.getAvailableLimit()));
        assertThat(currentAccount.isActiveCard(), Is.is(ACTIVE_ACCOUNT.isActiveCard()));
    }

}
