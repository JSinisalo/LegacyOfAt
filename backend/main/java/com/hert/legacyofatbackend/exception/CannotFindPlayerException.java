package com.hert.legacyofatbackend.exception;

/**
 * The type Cannot find player exception.
 */
public class CannotFindPlayerException extends CannotFindEntityException {

    /**
     * Instantiates a new Cannot find player exception.
     *
     * @param id the id
     */
    public CannotFindPlayerException(long id) {
        super(id);
    }
}

