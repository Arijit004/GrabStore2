package com.storeservice.exception;

public class UnauthorizeException extends RuntimeException {
    private String message;

    public UnauthorizeException(String message){
        super(message);
        this.message=message;
    }
}
