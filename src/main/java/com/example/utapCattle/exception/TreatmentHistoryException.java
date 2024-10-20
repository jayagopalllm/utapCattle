package com.example.utapCattle.exception;

import java.util.List;

public class TreatmentHistoryException extends Exception {

    private final List<String> validationErrors;

    public TreatmentHistoryException(List<String> error) {
        super("TreatmentHistory Exception");
        this.validationErrors = error;
    }

    public List<String> getErrors() {
        return validationErrors;
    }
}
