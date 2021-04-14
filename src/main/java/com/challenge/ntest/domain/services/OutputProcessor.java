package com.challenge.ntest.domain.services;

import java.util.function.Supplier;

public interface OutputProcessor<T> {

    void process(Supplier<T> fn);
}
