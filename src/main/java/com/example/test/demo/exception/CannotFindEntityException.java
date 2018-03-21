package com.example.test.demo.exception;

public class CannotFindEntityException extends IllegalArgumentException {

    private long id;

    public CannotFindEntityException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
