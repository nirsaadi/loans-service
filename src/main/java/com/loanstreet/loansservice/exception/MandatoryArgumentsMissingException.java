package com.loanstreet.loansservice.exception;

import java.util.Arrays;

public class MandatoryArgumentsMissingException extends RuntimeException {

    private static final long serialVersionUID = -8689864383618970395L;

    public MandatoryArgumentsMissingException(String[] arguments) {
        super(Arrays.toString(arguments));
    }
}
