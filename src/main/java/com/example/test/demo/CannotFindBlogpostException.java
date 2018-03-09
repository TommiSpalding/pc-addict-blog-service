package com.example.test.demo;

public class CannotFindBlogpostException extends IllegalArgumentException {

    private long id;

    public CannotFindBlogpostException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
