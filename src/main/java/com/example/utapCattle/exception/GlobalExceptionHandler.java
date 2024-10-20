package com.example.utapCattle.exception;

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

    @ExceptionHandler(CattleException.class)
    public ResponseEntity<String> handleCattleException(CattleException e) {
        String errorMessages = String.join(",", e.getErrors());
        return new ResponseEntity<>("Cattle Validation Errors:" + errorMessages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CommentException.class)
    public ResponseEntity<String> handleCommentException(CommentException e) {
        String errorMessages = String.join(",", e.getError());
        return new ResponseEntity<>("Comment Validation Errors:" + errorMessages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InductionException.class)
    public ResponseEntity<String> handleInductionException(InductionException e) {
        return new ResponseEntity<>("Induction Validation Errors:"+ e.getError(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SaleException.class)
    public ResponseEntity<String> handleSaleException(SaleException e) {
        return new ResponseEntity<>("SaleException Validation Errors:"+ e.getError(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TreatmentHistoryException.class)
    public ResponseEntity<String> handleTreatmentHistoryException(TreatmentHistoryException e) {
        String errorMessages = String.join(",", e.getErrors());
        return new ResponseEntity<>("TreatmentHistory Validation Errors:" + errorMessages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TbTestHistoryException.class)
    public ResponseEntity<String> handleTbTestHistoryException(TbTestHistoryException e) {
        return new ResponseEntity<>("TbTestHistory Validation Errors:"+ e.getError(), HttpStatus.BAD_REQUEST);
    }
}
