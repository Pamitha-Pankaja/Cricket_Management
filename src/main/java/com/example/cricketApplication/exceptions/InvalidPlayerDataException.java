package com.example.cricketApplication.exceptions;

public class InvalidPlayerDataException extends RuntimeException {
    public InvalidPlayerDataException(String message) {
        super(message);
    }
}

