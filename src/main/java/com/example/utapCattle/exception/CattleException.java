package com.example.utapCattle.exception;

import java.util.List;

public class CattleException extends Exception {

    private final List<String> validationErrors;

    public CattleException(List<String> error) {
        super("Cattle Exception");
        this.validationErrors = error;
    }

    public List<String> getErrors() {
        return validationErrors;
    }
}
