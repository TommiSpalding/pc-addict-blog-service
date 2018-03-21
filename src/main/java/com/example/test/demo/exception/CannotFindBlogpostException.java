package com.example.test.demo.exception;

public class CannotFindBlogpostException extends CannotFindEntityException {

    public CannotFindBlogpostException(long id) {
        super(id);
    }

}
