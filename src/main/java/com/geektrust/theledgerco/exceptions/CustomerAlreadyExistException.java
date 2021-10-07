package com.geektrust.theledgerco.exceptions;

public class CustomerAlreadyExistException extends ClientException {

    public CustomerAlreadyExistException(String message) {
        super(message);
    }
}
