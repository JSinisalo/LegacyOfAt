package com.hert.legacyofatbackend.exception;

public class CannotFindPlayerException extends CannotFindEntityException {

    public CannotFindPlayerException(long id) {
        super(id);
    }
}

