package com.example.utapCattle.exception;

import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.utapCattle.logging.GlobalRequestLogger;

@RestControllerAdvice(basePackages = "com.example.utapCattle.controller")
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @ExceptionHandler(DuplicateCattleException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateCattleId(DuplicateCattleException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap("message", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        logger.error("Exception occurred: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("message", getShortMessage(ex.getMessage())));
    }

    private String getShortMessage(String rawMessage) {
        String shortMessage;

        if (rawMessage != null && !rawMessage.isBlank()) {
            // Get only the first line of the message
            shortMessage = rawMessage.split("\n")[0];
        } else {
            // Fallback to exception class name
            shortMessage = "Unexpected error occurred while processing the request.";
        }
        return shortMessage;
    }

}
