package com.challenge.ntest;

import com.challenge.ntest.controller.OperationController;
import com.challenge.ntest.domain.commons.OperationMapper;
import com.challenge.ntest.domain.services.StdInProcessor;
import com.challenge.ntest.domain.services.StdOutProcessor;

public class ApplicationRunner {

    public static void main(String[] args) {
        new OperationController(
                new OperationMapper(),
                new StdInProcessor(),
                new StdOutProcessor()
        ).start();
    }
}
