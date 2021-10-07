package com.geektrust.theledgerco.exceptions;

public class InvalidPaymentCommandException extends ClientException {

    public InvalidPaymentCommandException(String message) {
        super(message);
    }

    public InvalidPaymentCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
