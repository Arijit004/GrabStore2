package com.authentication.exception;

//user not found exception...
public class UserNotFoundException extends RuntimeException{
    private String message;
    public UserNotFoundException(String message){
        super(message);
        this.message=message;
    }
}
