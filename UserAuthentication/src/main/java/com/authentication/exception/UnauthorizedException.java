package com.authentication.exception;

public class UnauthorizedException extends RuntimeException{
    private String message;

    public UnauthorizedException(String message){
        super(message);
        this.message=message;
    }
}
