package com.hert.legacyofatbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EntityExceptionHandler {

    @ExceptionHandler(CannotFindPlayerException.class)
    public ResponseEntity<ErrorInfo> handleConflict(CannotFindPlayerException ex) {

        ErrorInfo e = new ErrorInfo("Cannot find blog post with id: " + ex.getId());

        return new ResponseEntity<ErrorInfo>(e, HttpStatus.NOT_FOUND);
    }
}
