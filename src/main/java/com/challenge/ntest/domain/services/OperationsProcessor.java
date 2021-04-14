package com.challenge.ntest.domain.services;

import com.challenge.ntest.domain.commons.OperationMapper;

public class OperationsProcessor {

    final OperationMapper mapper;
    final InputProcessor<String> input;
    final OutputProcessor<String> output;

    public OperationsProcessor(OperationMapper mapper, InputProcessor<String> input, OutputProcessor<String> output) {
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
