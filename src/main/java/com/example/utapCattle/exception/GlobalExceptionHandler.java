package com.example.utapCattle.exception;

import jakarta.xml.bind.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e){
        return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CattleValidationException.class)
    public ResponseEntity<String> handleCattleValidationException(CattleValidationException e) {
        String errorMessages = String.join(",", e.getErrors());
        return new ResponseEntity<>("Validation Errors:" + errorMessages, HttpStatus.BAD_REQUEST);
    }
}
