package com.payment.exception;

public class CartAlreadyExistsException extends RuntimeException {
    private String message;

    public CartAlreadyExistsException(String message){
        super(message);
        this.message=message;
    }
}
