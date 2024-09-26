package com.storeservice.exception;

public class StoreNotFoundException extends RuntimeException{
    private String message;
    public StoreNotFoundException(String message){
        super(message);
        this.message=message;
    }
}
