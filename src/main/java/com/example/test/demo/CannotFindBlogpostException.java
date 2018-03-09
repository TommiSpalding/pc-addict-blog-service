package com.example.test.demo;

public class CannotFindBlogpostException extends CannotFindEntityException {

    public CannotFindBlogpostException(long id) {
        super(id);
    }

}
