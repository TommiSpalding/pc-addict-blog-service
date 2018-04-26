package com.example.test.demo.exception;

/**
 * The type Cannot find blogpost exception.
 */
public class CannotFindBlogpostException extends CannotFindEntityException {

    /**
     * Instantiates a new Cannot find blogpost exception.
     *
     * @param id the id
     */
    public CannotFindBlogpostException(long id) {
        super(id);
    }

}
