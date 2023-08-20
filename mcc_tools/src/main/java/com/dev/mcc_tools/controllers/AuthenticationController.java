package com.dev.mcc_tools.controllers;

import com.dev.mcc_tools.domain.User;
import com.dev.mcc_tools.email.EmailSenderService;
import com.dev.mcc_tools.security.JwtService;
import com.dev.mcc_tools.services.AuthenticationService;
import com.dev.mcc_tools.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        HttpStatus httpStatus = HttpStatus.CREATED;

        String unHashed = user.getPassword();

        user.setPassword(encoder.encode(unHashed));
        User created = userService.saveOrUpdateUser(user);
        FormattedResponse response = new FormattedResponse(httpStatus.value(), true, created);

//        senderService.sendEmail(user.getEmail(), "Account Created", "Your account has been created. " + unHashed + " is your temporary password, click this link to get started");

        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest) {
        HttpStatus httpStatus = HttpStatus.OK;
        AuthenticationResponse authResponse = authenticationService.login(authRequest);
        FormattedResponse response = new FormattedResponse(httpStatus.value(), true, authResponse);

        return new ResponseEntity<>(response, httpStatus);
    }
    @GetMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestParam String email){
        HttpStatus httpStatus = HttpStatus.OK;
        User found = userService.findUserByEmail(email);
        FormattedResponse response = new FormattedResponse(httpStatus.value(), true, found);
        return new ResponseEntity<>(response, httpStatus);
    }
}