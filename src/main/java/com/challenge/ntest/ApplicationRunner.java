package com.challenge.ntest;

import com.challenge.ntest.domain.commons.OperationMapper;
import com.challenge.ntest.domain.services.OperationsProcessor;
import com.challenge.ntest.domain.services.StdInProcessor;
import com.challenge.ntest.domain.services.StdOutProcessor;

public class ApplicationRunner {

    public static void main(String[] args) {
        new OperationsProcessor(
                new OperationMapper(),
                new StdInProcessor(),
                new StdOutProcessor()
        ).start();
    }
}
