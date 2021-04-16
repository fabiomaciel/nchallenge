package com.challenge.nuchallenge.domain.commons;

import com.challenge.nuchallenge.domain.models.Account;
import com.challenge.nuchallenge.domain.models.Transaction;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class OperationMapperTest {

    public static final OperationMapper mapper = new OperationMapper();

    @Test
    public void shoudlConvertAccountProperly() throws IOException {
        String input = "{\"account\": {\"active-card\": true, \"available-limit\": 100}}";

        Object object = mapper.convertValue(input);

        assertThat(object, instanceOf(Account.class));
    }

    @Test
    public void shoudlConvertTransactionProperly() throws IOException {
        String input = "{\"transaction\": {\"merchant\": \"Burger King\", \"amount\": 20, \"time\": \"2019-02-13T10:00:00.000Z\"}}";


        Object object = mapper.convertValue(input);

        assertThat(object, instanceOf(Transaction.class));
    }

    @Test
    public void shoudlThrownAnErrorIfTheOperationIsNotMapped() {
        String input = "{\"not-mapped-type\": {\"merchant\": \"Burger King\", \"amount\": 20, \"time\": \"2019-02-13T10:00:00.000Z\"}}";

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mapper.convertValue(input);
        });

    }

    @Test
    public void shoudlThrownAnErrorIfTheFildsNotMatch() {
        String input = "{\"account\": {\"merchant\": \"Burger King\", \"amount\": 20, \"time\": \"2019-02-13T10:00:00.000Z\"}}";

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mapper.convertValue(input);
        });

    }

    @Test
    public void shoudlThrownAnErrorIfTheJsonIsUnformatted() {
        String input = "{\"}";

        Assertions.assertThrows(IOException.class, () -> {
            mapper.convertValue(input);
        });
    }

    @Test
    public void shouldConvertAListToStringSeparatedByNewLine() throws IOException {
        List<String> list = Arrays.asList("1", "2", "3");

        String str = mapper.writeListAsString(list);
        String expected = "\"1\"\n\"2\"\n\"3\"\n";
        assertThat(str, Is.is(expected));
    }

}
