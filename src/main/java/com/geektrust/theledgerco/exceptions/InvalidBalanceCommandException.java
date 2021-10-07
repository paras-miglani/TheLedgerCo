package com.geektrust.theledgerco.exceptions;

public class InvalidBalanceCommandException extends ClientException {

    public InvalidBalanceCommandException(String message) {
        super(message);
    }

    public InvalidBalanceCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
