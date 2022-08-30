package com.example.javaproject.exception;

public class UserNotFound extends RuntimeException {
   public UserNotFound(String message) {

       super(message);
    }
}
