package com.challenge.ntest.domain.services;

import java.util.Scanner;
import java.util.function.Consumer;

public class StdInProcessor implements InputProcessor<String> {

    @Override
    public void process(Consumer<String> consumer) {
        Scanner input = new Scanner(System.in);

        while (input.hasNext()) {
            consumer.accept(input.nextLine());
        }
    }
}
