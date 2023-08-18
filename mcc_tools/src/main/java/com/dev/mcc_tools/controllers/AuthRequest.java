package com.dev.mcc_tools.controllers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AuthRequest {
    private String email;
    private String password;
    private String role;
}
