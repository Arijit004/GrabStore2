package com.storeservice.exception;

public class StoreAlreadyExistsException extends RuntimeException{
    private String message;
    public StoreAlreadyExistsException(String message){
        super(message);
        this.message=message;
    }
}
