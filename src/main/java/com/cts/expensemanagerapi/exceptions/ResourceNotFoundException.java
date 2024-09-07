package com.cts.expensemanagerapi.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -4952662894253315127L;
    public ResourceNotFoundException(String message) {
        super(message);
    }


}