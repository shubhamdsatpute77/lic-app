package com.sds.backend.exception;

public class ResourceNotFoundException extends ValidationException {

    public ResourceNotFoundException(String error, Object invalidData) {
        super(error, invalidData);
    }
}
