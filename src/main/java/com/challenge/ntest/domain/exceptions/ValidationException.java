package com.challenge.ntest.domain.exceptions;

import com.challenge.ntest.domain.models.ViolationType;

public class ValidationException extends RuntimeException {

    public ValidationException(ViolationType violation) {
        super(violation.getDescription());
    }

    public ValidationException() {
    }
}
