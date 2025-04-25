package com.example.agrodirect.exceptions;

public class LoginCredentialsException extends IllegalArgumentException{

    public LoginCredentialsException (String message) {

        super(message);
    }
}
