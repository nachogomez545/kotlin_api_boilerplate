package com.rest.api.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseBody
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleIOException(final CustomException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                new CustomErrorResponse(
                        ex.getTimestamp(),
                        ex.getStatus().value(),
                        ex.getCode(),
                        ex.getMessage(),
                        ex.getDetails()
                ),
                new HttpHeaders(), ex.getStatus(), request);
    }
}