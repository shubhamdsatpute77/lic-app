package com.sds.backend.exception;

import lombok.Getter;

@Getter
public class BussinessValidationException extends RuntimeException {
    private Object invalidData;

    public BussinessValidationException(String error, Object invalidData) {
        super(error);
        this.invalidData = invalidData;
    }
}
