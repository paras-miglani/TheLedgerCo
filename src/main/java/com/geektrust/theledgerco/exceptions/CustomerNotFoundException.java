package com.geektrust.theledgerco.exceptions;

public class CustomerNotFoundException extends ClientException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
