package com.tka.electosphere.exceptions;

public class VoterNotFoundException extends RuntimeException {
    public VoterNotFoundException(String message) {
        super(message);
    }
}
