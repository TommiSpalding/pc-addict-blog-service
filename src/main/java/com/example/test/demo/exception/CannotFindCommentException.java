package com.example.test.demo.exception;

public class CannotFindCommentException extends CannotFindEntityException {

    public CannotFindCommentException(long id) {
        super(id);
    }

}
