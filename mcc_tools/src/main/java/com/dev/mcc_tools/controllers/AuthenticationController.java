package com.dev.mcc_tools.controllers;

import com.dev.mcc_tools.domain.User;
import com.dev.mcc_tools.email.EmailSenderService;
import com.dev.mcc_tools.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private EmailSenderService senderService;

    @Autowired
    private PasswordEncoder encoder;

//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser( @RequestBody User user) {
//        HttpStatus httpStatus = HttpStatus.CREATED;
//
////        String unHashed = user.getPassword();
////
////        user.setPassword(encoder.encode(unHashed));
//        FormattedResponse response = userService.saveUser(user);
//
////        senderService.sendEmail(user.getEmail(), "Account Created", "Your account has been created. " + unHashed + " is your temporary password, click this link to get started");
//
//        return new ResponseEntity<>(response, httpStatus);
//    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser( @RequestBody RegistrationRequest request) {



        FormattedResponse response = userService.saveUser(request);

//        senderService.sendEmail(user.getEmail(), "Account Created", "Your account has been created. " + unHashed + " is your temporary password, click this link to get started");

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest) {
        FormattedResponse response = authenticationService.login(authRequest);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }


}