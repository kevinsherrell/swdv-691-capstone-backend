package com.dev.mcc_tools.controllers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@RequiredArgsConstructor
public class GeneralErrorResponse extends GeneralResponse{
    private HashMap<String, String > errors;

    public void addError(String field, String message) {
        errors.put(field, message);
    }

}
