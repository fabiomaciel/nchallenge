package com.challenge.ntest.controller;

import com.challenge.ntest.domain.commons.OperationMapper;
import com.challenge.ntest.domain.services.InputProcessor;
import com.challenge.ntest.domain.services.OutputProcessor;
import com.challenge.ntest.domain.services.StateManager;

public class OperationController {

    final OperationMapper mapper;
    final InputProcessor<String> input;
    final OutputProcessor<String> output;

    public OperationController(OperationMapper mapper, InputProcessor<String> input, OutputProcessor<String> output) {
        this.mapper = mapper;
        this.input = input;
        this.output = output;
    }

    public void start() {

        final StateManager manager = new StateManager();

        input.process(line -> {
            final var operation = mapper.convertValue(line);
            manager.performOperation(operation);
        });

        output.process(() -> mapper.writeListAsString(manager.getHistory()));

    }


}
