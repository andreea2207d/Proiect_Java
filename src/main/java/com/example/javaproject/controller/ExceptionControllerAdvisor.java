package com.example.javaproject.controller;

import com.example.javaproject.exception.*;
import com.example.javaproject.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

   @ExceptionHandler(UserNotFound.class)
   public ResponseEntity<Object> handleUserNotFoundException(UserNotFound ex) {
       return new ResponseEntity<>(Util.generateErrorBody(ex.getMessage()), HttpStatus.NOT_FOUND);
   }

    @ExceptionHandler(NotMatchingPassword.class)
    public ResponseEntity<Object> handleNotMatchingPasswordException(NotMatchingPassword ex) {
        return new ResponseEntity<>(Util.generateErrorBody(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyRegistered.class)
    public ResponseEntity<Object> handleEmailAlreadyUsedException(EmailAlreadyRegistered ex) {
        return new ResponseEntity<>(Util.generateErrorBody(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ClothingNotFound.class)
    public ResponseEntity<Object> handlePostNotFoundException(ClothingNotFound ex) {
        return new ResponseEntity<>(Util.generateErrorBody(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReviewNotFound.class)
    public ResponseEntity<Object> handleMessageNotFoundException(ReviewNotFound ex) {
        return new ResponseEntity<>(Util.generateErrorBody(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ShoppingCartNotFound.class)
    public ResponseEntity<Object> handleCommentNotFoundException(ShoppingCartNotFound ex) {
        return new ResponseEntity<>(Util.generateErrorBody(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFound.class)
    public ResponseEntity<Object> handleCategoryNotFoundException(OrderNotFound ex) {
        return new ResponseEntity<>(Util.generateErrorBody(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

}
