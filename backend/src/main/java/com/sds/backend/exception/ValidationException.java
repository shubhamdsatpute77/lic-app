package com.sds.backend.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
    private Object invalidData;

    public ValidationException(String error, Object invalidData) {
        super(error);
        this.invalidData = invalidData;
    }
}
