package com.example.agrodirect.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }
}
