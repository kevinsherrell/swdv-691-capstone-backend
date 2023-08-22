package com.dev.mcc_tools.controllers;

import com.dev.mcc_tools.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private User user;
    private HashMap<String, String> errors;

    public void addErrors(String field, String message) {
        errors.put(field, message);
    }
}
