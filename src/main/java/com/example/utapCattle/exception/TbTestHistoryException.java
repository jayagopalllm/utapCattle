package com.example.utapCattle.exception;

public class TbTestHistoryException extends Exception {

    private final String error;

    public TbTestHistoryException(String error) {
        super("TbtestHistory Exception");
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
