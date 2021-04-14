package com.challenge.ntest.domain.services;

import java.util.function.Consumer;

public interface InputProcessor<T> {

    void process(Consumer<T> consumer);

}
