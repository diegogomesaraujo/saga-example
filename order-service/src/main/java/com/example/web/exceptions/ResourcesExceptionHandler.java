package com.example.web.exceptions;

import com.example.domain.exception.NotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ResourcesExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    public ErrorDTO notFound(RuntimeException e) {
        return ErrorDTO.builder()
                .code(NOT_FOUND.value())
                .error(NOT_FOUND.getReasonPhrase())
                .build();
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorDTO genericError(Exception e) {
        return ErrorDTO.builder()
                .code(INTERNAL_SERVER_ERROR.value())
                .error(INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(e.getMessage())
                .build();
    }

}
