package com.tka.electosphere.exceptions;

public class ElectionNotFoundException extends RuntimeException {
    public ElectionNotFoundException(String message) {
        super(message);
    }

    public ElectionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
