package com.stupor.zmr.exception.errors;

import lombok.Getter;

@Getter
public class ExternalServiceException extends RuntimeException {
    private final boolean timeout;

    public ExternalServiceException(String message, boolean timeout) {
        super(message);
        this.timeout = timeout;
    }

}