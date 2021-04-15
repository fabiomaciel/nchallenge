package com.challenge.ntest.infrastructure.processor.standard;

import com.challenge.ntest.infrastructure.processor.OutputProcessor;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.hamcrest.MatcherAssert.assertThat;

public class StdOutProcessorTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void shouldVerifyIfContentIsDeliveryProperly() {
        OutputProcessor<String> outputProcessor = new StdOutProcessor();
        String outContent = "test";
        outputProcessor.process(() -> outContent);


        assertThat(outputStream.toString().trim(), Is.is(outContent));
    }

}
