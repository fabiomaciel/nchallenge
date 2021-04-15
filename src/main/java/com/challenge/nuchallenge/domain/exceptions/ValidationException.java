package com.challenge.nuchallenge.domain.exceptions;

import com.challenge.nuchallenge.domain.models.ViolationType;

public class ValidationException extends RuntimeException {

    public ValidationException(ViolationType violation) {
        super(violation.getDescription());
    }

    public ValidationException() {
    }
}
