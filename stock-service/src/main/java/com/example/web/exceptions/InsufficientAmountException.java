package com.example.web.exceptions;

public class InsufficientAmountException extends RuntimeException {

    public InsufficientAmountException() {
    }

    public InsufficientAmountException(String message) {
        super(message);
    }

    public InsufficientAmountException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientAmountException(Throwable cause) {
        super(cause);
    }

    public InsufficientAmountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
