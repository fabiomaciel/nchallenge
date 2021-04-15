package com.challenge.nuchallenge;

import com.challenge.nuchallenge.controller.OperationsController;
import com.challenge.nuchallenge.domain.commons.OperationMapper;
import com.challenge.nuchallenge.infrastructure.processor.standard.StdInProcessor;
import com.challenge.nuchallenge.infrastructure.processor.standard.StdOutProcessor;

public class ApplicationRunner {

    public static void main(String[] args) {
        new OperationsController(
                new OperationMapper(),
                new StdInProcessor(),
                new StdOutProcessor()
        ).start();
    }
}
