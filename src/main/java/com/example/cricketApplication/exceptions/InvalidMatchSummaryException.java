package com.example.cricketApplication.exceptions;

public class InvalidMatchSummaryException extends RuntimeException {
    public InvalidMatchSummaryException(String message) {
        super(message);
    }
}
