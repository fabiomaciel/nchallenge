package com.challenge.ntest.controller;

import com.challenge.ntest.domain.commons.OperationMapper;
import com.challenge.ntest.domain.services.AccountStateManager;
import com.challenge.ntest.infrastructure.processor.InputProcessor;
import com.challenge.ntest.infrastructure.processor.OutputProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;

public class OperationsController {

    final OperationMapper mapper;
    final InputProcessor<String> input;
    final OutputProcessor<String> output;

    public OperationsController(OperationMapper mapper, InputProcessor<String> input, OutputProcessor<String> output) {
        this.mapper = mapper;
        this.input = input;
        this.output = output;
    }

    public void start() {
        final AccountStateManager manager = new AccountStateManager();

        input.process(line -> processInputLine(line, manager));
        output.process(() -> processOutput(manager));

    }

    private void processInputLine(String line, AccountStateManager manager) {
        try {
            final var operation = mapper.convertValue(line);
            manager.performOperation(operation);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    private String processOutput(AccountStateManager manager) {
        try {
            return mapper.writeListAsString(manager.getHistory());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
