package com.challenge.ntest.domain.services;

import java.util.function.Supplier;

public class StdOutProcessor implements OutputProcessor<String> {

    @Override
    public void process(Supplier<String> fn) {
        System.out.println(fn.get());
    }
}
