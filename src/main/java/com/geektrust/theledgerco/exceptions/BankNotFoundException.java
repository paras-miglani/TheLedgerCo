package com.geektrust.theledgerco.exceptions;

public class BankNotFoundException extends ClientException {

    public BankNotFoundException(String message) {
        super(message);
    }
}
