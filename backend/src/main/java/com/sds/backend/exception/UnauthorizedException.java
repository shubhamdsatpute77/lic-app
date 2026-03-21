package com.sds.backend.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String error) {
        super(error);
    }
}
