package com.dev.mcc_tools.controllers;

import com.dev.mcc_tools.domain.User;
import com.dev.mcc_tools.security.JwtService;
import com.dev.mcc_tools.services.AuthenticationService;
import com.dev.mcc_tools.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        HttpStatus httpStatus = HttpStatus.CREATED;

        User created = userService.saveOrUpdateUser(user);
        FormattedResponse response = new FormattedResponse(httpStatus.value(), true, created);
        System.out.println(user.getPassword());
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest) {
        HttpStatus httpStatus = HttpStatus.OK;

        System.out.println(authRequest.getEmail());
        System.out.println(authRequest.getPassword());
        String token = authenticationService.login(authRequest);
        FormattedResponse response = new FormattedResponse(httpStatus.value(), true, token);

        return new ResponseEntity<>(response, httpStatus);
    }
}