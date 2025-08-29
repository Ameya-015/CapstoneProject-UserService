package com.capstone.userauthservice.exceptions;

public class UserAlreadyExistsExceptions extends RuntimeException{
    public UserAlreadyExistsExceptions(String message) {
        super(message);
    }
}
