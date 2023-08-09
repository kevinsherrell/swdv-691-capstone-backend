package com.dev.mcc_tools.controllers;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

//    @ExceptionHandler(value = ConfigDataResourceNotFoundException.class)
//    public HttpEntity<?> resourceNotFoundException(ConfigDataResourceNotFoundException ex, WebRequest request) {
////        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), false, "resource not found");
//        return new HttpEntity<>(response);
//    }

}
