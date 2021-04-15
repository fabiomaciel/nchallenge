package com.challenge.nuchallenge.controller;

import com.challenge.nuchallenge.domain.commons.OperationMapper;
import com.challenge.nuchallenge.infrastructure.processor.InputProcessor;
import com.challenge.nuchallenge.infrastructure.processor.OutputProcessor;
import com.challenge.nuchallenge.infrastructure.processor.standard.StdInProcessor;
import com.challenge.nuchallenge.infrastructure.processor.standard.StdOutProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.io.*;
import java.util.Scanner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;

public class OperationsControllerTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void shouldCalMethodsProperly() throws JsonProcessingException {
        OperationMapper mapper = Mockito.spy(new OperationMapper());
        InputProcessor inputProcessor = Mockito.mock(InputProcessor.class);
        OutputProcessor outputProcessor = Mockito.mock(OutputProcessor.class);

        OperationsController controller = new OperationsController(mapper, inputProcessor, outputProcessor);

        System.setIn(
                new ByteArrayInputStream("".getBytes())
        );

        controller.start();

        Mockito.verify(inputProcessor).process(any());
        Mockito.verify(outputProcessor).process(any());

    }

    @ParameterizedTest
    @ValueSource(strings = {
            "insuficient-limit",
            "account-already-initialized",
            "high-frequency",
            "double-transaction",
            "insuficient-limit+high-frequency",
            "insuficient-limit+double-transaction"

    })
    public void shouldProcessInputAndOutputProperly(String folder){
        OperationMapper mapper = new OperationMapper();
        InputProcessor<String> inputProcessor = new StdInProcessor();
        OutputProcessor<String> outputProcessor = new StdOutProcessor();

        OperationsController controller = new OperationsController(mapper, inputProcessor, outputProcessor);

        System.setIn(getClass().getClassLoader().getResourceAsStream(String.format("%s/input", folder)));
        String expectedOutput = sanitize(getFileAsString(String.format("%s/output", folder)));

        controller.start();

        assertThat(sanitize(outputStream.toString()), Is.is(expectedOutput));
    }



    private String sanitize(String str){
        return str.replaceAll(" ?\n?\r?", "");
    }

    private String getFileAsString(String fileName){
        InputStream outExpectedStream = getClass().getClassLoader().getResourceAsStream(fileName);
        Scanner scanner = new Scanner(outExpectedStream);
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNext()){
            builder.append(scanner.nextLine());
            builder.append("\n");
        }
        return builder.toString();
    }

}

