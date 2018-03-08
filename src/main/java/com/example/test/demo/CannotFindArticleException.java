package com.example.test.demo;

public class CannotFindArticleException extends IllegalArgumentException {

    private long id;

    public CannotFindArticleException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
