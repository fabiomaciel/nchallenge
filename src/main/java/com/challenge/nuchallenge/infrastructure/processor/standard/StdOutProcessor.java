package com.challenge.nuchallenge.infrastructure.processor.standard;

import com.challenge.nuchallenge.infrastructure.processor.OutputProcessor;
import java.util.function.Supplier;

public class StdOutProcessor implements OutputProcessor<String> {

    @Override
    public void process(Supplier<String> fn) {
        System.out.println(fn.get());
    }
}
