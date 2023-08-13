package com.dev.mcc_tools.exceptions;

import com.dev.mcc_tools.controllers.ErrorResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;


@ControllerAdvice
public class RequestExceptionHandler {
    @ExceptionHandler(value={NotFoundException.class})
    public ResponseEntity<?> handleRequestException(RequestException e){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        NotFoundException exception = new NotFoundException(
                e.getMessage(),
                e,
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(exception, httpStatus);
    }
}