package com.challenge.sea.exception;

public class AdoptionException extends RuntimeException {
    public AdoptionException(String message) {
        super(message);
    }

    public AdoptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
