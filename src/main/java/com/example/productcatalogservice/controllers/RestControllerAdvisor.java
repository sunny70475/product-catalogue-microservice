package com.example.productcatalogservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerAdvisor {
    /**
     * This method handles exceptions thrown by the controller methods.
     * It returns a ResponseEntity with a BAD_REQUEST status and the exception message.
     *
     * @param exception the exception that was thrown
     * @return a ResponseEntity containing the error message and HTTP status
     */
    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    public ResponseEntity<String> handleExceptions(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}