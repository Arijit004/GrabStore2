package com.payment.exception;

public class CartNotFoundException extends RuntimeException {
    private String message;

    public CartNotFoundException(String message){
        super(message);
        this.message=message;
    }
}
