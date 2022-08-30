package com.example.javaproject.exception;

public class ShoppingCartNotFound extends RuntimeException {
    public ShoppingCartNotFound(String message) {

        super(message);
    }
}
