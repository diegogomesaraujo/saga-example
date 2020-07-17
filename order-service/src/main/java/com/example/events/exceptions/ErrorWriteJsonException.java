package com.example.events.exceptions;

public class ErrorWriteJsonException extends RuntimeException {

    public ErrorWriteJsonException() {
    }

    public ErrorWriteJsonException(String message) {
        super(message);
    }

    public ErrorWriteJsonException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorWriteJsonException(Throwable cause) {
        super(cause);
    }

    public ErrorWriteJsonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
