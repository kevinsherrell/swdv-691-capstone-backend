package com.dev.mcc_tools.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class NotFoundException extends RuntimeException {
    private String message;
    private Throwable throwable;
    private HttpStatus httpStatus;
    private ZonedDateTime timestamp;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable throwable, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.message = message;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public NotFoundException(String message, String message1, Throwable throwable, HttpStatus httpStatus, ZonedDateTime timestamp) {
        super(message);
        this.message = message1;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public NotFoundException(String message, Throwable cause, String message1, Throwable throwable, HttpStatus httpStatus, ZonedDateTime timestamp) {
        super(message, cause);
        this.message = message1;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public NotFoundException(Throwable cause, String message, Throwable throwable, HttpStatus httpStatus, ZonedDateTime timestamp) {
        super(cause);
        this.message = message;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message1, Throwable throwable, HttpStatus httpStatus, ZonedDateTime timestamp) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message = message1;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
