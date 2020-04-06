package com.reach52.asssignment.exceptions;

public class InvalidParametersException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidParametersException() {
        super();
    }

    public InvalidParametersException(String errors) {
        super(errors);
    }
}
