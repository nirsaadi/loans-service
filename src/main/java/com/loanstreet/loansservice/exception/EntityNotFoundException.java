package com.loanstreet.loansservice.exception;

public class EntityNotFoundException extends RuntimeException{
    private static final long serialVersionUID = -8689864383618970395L;

    public EntityNotFoundException(String id) {
        super(id);
    }
}

