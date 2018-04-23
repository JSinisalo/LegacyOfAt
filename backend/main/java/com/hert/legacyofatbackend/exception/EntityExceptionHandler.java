package com.hert.legacyofatbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The type Entity exception handler.
 */
@ControllerAdvice
public class EntityExceptionHandler {

    /**
     * Handle conflict response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(CannotFindPlayerException.class)
    public ResponseEntity<ErrorInfo> handleConflict(CannotFindPlayerException ex) {

        ErrorInfo e = new ErrorInfo("Cannot find blog post with id: " + ex.getId());

        return new ResponseEntity<ErrorInfo>(e, HttpStatus.NOT_FOUND);
    }
}
