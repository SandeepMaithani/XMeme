package com.api.XmemeBackend.exception;

import org.springframework.http.HttpStatus;

public class MemeWrongUpdateException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public MemeWrongUpdateException(String message) {
        super(message);
    }

    public static String updationNotAllowedException(String id) {
        int statusCode = HttpStatus.FORBIDDEN.value();
        return String.valueOf(statusCode);
    }
    
}
