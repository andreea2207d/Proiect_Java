package com.example.javaproject.exception;

public class NotMatchingPassword extends RuntimeException {

    public NotMatchingPassword(String message) {

        super(message);
    }
}
