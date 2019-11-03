package com.rest.api.exceptions;

import org.springframework.http.HttpStatus;

import java.time.Instant;

abstract public class CustomException extends RuntimeException {

    private static final long serialVersionUID = -5194625393897364963L;

    private Long timestamp;
    private String message;
    private HttpStatus status;
    private String code;
    private String details;

    CustomException(String message, HttpStatus status, String code) {
        this.message = message;
        this.status = status;
        this.timestamp = Instant.now().getEpochSecond();
        this.code = code;
    }

    CustomException(String message, HttpStatus status, String code, String details) {
        this.message = message;
        this.status = status;
        this.timestamp = Instant.now().getEpochSecond();
        this.code = code;
        this.details = details;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}