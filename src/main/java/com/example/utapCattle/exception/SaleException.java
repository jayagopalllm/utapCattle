package com.example.utapCattle.exception;

public class SaleException extends Exception{

    private final String error;

    public SaleException (String error) {
        super ( "Sale Exception");
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
