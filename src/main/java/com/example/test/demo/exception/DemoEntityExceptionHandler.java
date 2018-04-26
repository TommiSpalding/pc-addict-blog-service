package com.example.test.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The type Demo entity exception handler.
 */
@ControllerAdvice
public class DemoEntityExceptionHandler {

    /**
     * Handle conflict response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(CannotFindBlogpostException.class)
    public ResponseEntity<ErrorInfo> handleConflict(CannotFindBlogpostException ex) {

        ErrorInfo e = new ErrorInfo("Cannot find blog post with id: " + ex.getId());

        return new ResponseEntity<ErrorInfo>(e, HttpStatus.NOT_FOUND);
    }

    /**
     * Comment not found response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(CannotFindCommentException.class)
    public ResponseEntity<ErrorInfo> commentNotFound(CannotFindCommentException ex) {

        ErrorInfo e = new ErrorInfo("Cannot find comment with id: " + ex.getId());

        return new ResponseEntity<ErrorInfo>(e, HttpStatus.NOT_FOUND);
    }
}
