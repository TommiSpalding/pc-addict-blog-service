package com.example.test.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DemoEntityExceptionHandler {

    @ExceptionHandler(CannotFindArticleException.class)
    public ResponseEntity<ErrorInfo> handleConflict(CannotFindArticleException ex) {

        ErrorInfo e = new ErrorInfo("Cannot find article with id: " + ex.getId());

        return new ResponseEntity<ErrorInfo>(e, HttpStatus.NOT_FOUND);
    }
}
