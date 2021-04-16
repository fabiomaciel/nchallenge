package com.challenge.nuchallenge.infrastructure.processor.standard;

import com.challenge.nuchallenge.infrastructure.processor.InputProcessor;
import java.io.ByteArrayInputStream;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class StdInProcessorTest {



    @Test

    public void shouldVerifyIfContentIsDeliveryProperly() {
        InputProcessor<String> inputProcessor = new StdInProcessor();
        String inputContent = "test";

        System.setIn(new ByteArrayInputStream(inputContent.getBytes()));

        inputProcessor.process((line) -> {
            assertThat(line, Is.is(inputContent));
        });
    }

}
