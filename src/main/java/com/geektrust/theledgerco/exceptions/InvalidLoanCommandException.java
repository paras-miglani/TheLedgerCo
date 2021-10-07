package com.geektrust.theledgerco.exceptions;

public class InvalidLoanCommandException extends ClientException {
    public InvalidLoanCommandException(String message) {
        super(message);
    }

    public InvalidLoanCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
