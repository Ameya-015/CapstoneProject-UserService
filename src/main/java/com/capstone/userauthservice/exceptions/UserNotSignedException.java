package com.capstone.userauthservice.exceptions;

public class UserNotSignedException extends RuntimeException{
    public UserNotSignedException(String message) {
        super(message);
    }
}
