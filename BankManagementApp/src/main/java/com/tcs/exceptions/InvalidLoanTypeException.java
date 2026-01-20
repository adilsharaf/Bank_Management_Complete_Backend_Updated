package com.tcs.exceptions;

public class InvalidLoanTypeException extends RuntimeException {

	public InvalidLoanTypeException(String message) {
        super(message);
    }
}
