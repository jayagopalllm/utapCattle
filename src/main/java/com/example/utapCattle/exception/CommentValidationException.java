package com.example.utapCattle.exception;

public class CommentValidationException extends Exception {

    private final String error;

    public CommentValidationException(String error) {
        super("Comment Validation Exception");
        this.error = error;
    }

    public String getError(){
        return error;
    }


}
