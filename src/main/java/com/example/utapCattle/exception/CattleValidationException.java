package com.example.utapCattle.exception;

import java.util.List;

public class CattleValidationException extends Exception {

    private final List<String> validationErrors;

    public CattleValidationException(List<String> error) {
        super("Cattle Validation Exception");
        this.validationErrors = error;
    }

    public List<String> getErrors() {
        return validationErrors;
    }
}
