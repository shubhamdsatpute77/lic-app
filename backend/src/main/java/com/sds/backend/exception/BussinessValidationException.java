package com.sds.backend.exception;

public class BussinessValidationException extends RuntimeException {
    public BussinessValidationException(String error) {
        super(error);
    }
}
