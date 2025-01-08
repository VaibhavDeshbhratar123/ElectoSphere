package com.tka.electosphere.exceptions;

public class VoteNotFoundException extends RuntimeException {
    public VoteNotFoundException(String message) {
        super(message);
    }
}
