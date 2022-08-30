package com.example.javaproject.exception;

public class ClothingNotFound extends RuntimeException {
    public ClothingNotFound(String message) {
        super(message);
    }
}
