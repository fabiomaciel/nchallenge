package com.challenge.ntest;

import com.challenge.ntest.controller.OperationsController;
import com.challenge.ntest.domain.commons.OperationMapper;
import com.challenge.ntest.infrastructure.processor.standard.StdInProcessor;
import com.challenge.ntest.infrastructure.processor.standard.StdOutProcessor;

public class ApplicationRunner {

    public static void main(String[] args) {
        new OperationsController(
                new OperationMapper(),
                new StdInProcessor(),
                new StdOutProcessor()
        ).start();
    }
}
