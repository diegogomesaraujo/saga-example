package com.example.events.converter;

public class DataConverterException extends  RuntimeException {

    public DataConverterException() {
    }

    public DataConverterException(String message) {
        super(message);
    }

    public DataConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataConverterException(Throwable cause) {
        super(cause);
    }

    public DataConverterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
