package com.challenge.ntest.infrastructure.processor;

import java.util.function.Supplier;

public interface OutputProcessor<T> {

    void process(Supplier<T> fn);
}
