package com.tcs.exceptions;

public class LoanClosedException extends RuntimeException {
    public LoanClosedException(String message) {
        super(message);
    }
}
