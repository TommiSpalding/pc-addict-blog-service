package com.example.test.demo.exception;

/**
 * The type Cannot find entity exception.
 */
public class CannotFindEntityException extends IllegalArgumentException {

    private long id;

    /**
     * Instantiates a new Cannot find entity exception.
     *
     * @param id the id
     */
    public CannotFindEntityException(long id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }
}
