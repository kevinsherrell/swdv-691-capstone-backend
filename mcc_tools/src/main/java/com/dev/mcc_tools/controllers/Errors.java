package com.dev.mcc_tools.controllers;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
public class Errors {

    private HashMap<String, String> errors = new HashMap<>();
    private String field;
    private String message;
    public Errors(String field, String message){
        this.field = field;
        this.message = message;
    }

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public void addError(String field, String message) {
       this.errors.put(field, message);
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}