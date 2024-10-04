package com.example.utapCattle.exception;

public class InductionException extends Exception {

    private final String error;

    public InductionException(String error) {
        super("Induction Exception");
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
