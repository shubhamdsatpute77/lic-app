package com.sds.backend.exception;

public class BadRequestException extends ValidationException {

    public BadRequestException(String error, Object invalidData) {
        super(error, invalidData);
    }
}
