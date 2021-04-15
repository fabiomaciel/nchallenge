package com.challenge.nuchallenge.domain.models;

import java.util.LinkedList;

public class Violations extends LinkedList<String> {

    public boolean add(ViolationType violation) {
        return super.add(violation.getDescription());
    }
}
