package com.challenge.ntest.infrastructure.processor;

import java.util.function.Consumer;

public interface InputProcessor<T> {

    void process(Consumer<T> consumer);

}
