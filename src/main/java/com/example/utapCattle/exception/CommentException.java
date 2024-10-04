package com.example.utapCattle.exception;

public class CommentException extends Exception {

    private final String error;

    public CommentException(String error) {
        super("Comment Exception");
        this.error = error;
    }

    public String getError(){
        return error;
    }

}
