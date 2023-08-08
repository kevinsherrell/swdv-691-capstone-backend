package com.dev.mcc_tools.controllers;

import com.dev.mcc_tools.domain.User;
import com.dev.mcc_tools.services.UserService;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.Format;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    public HttpEntity<?> createNewUser(@Valid @RequestBody User user, BindingResult result) {
        User created = userService.saveOrUpdateUser(user);
        FormattedResponse response = new FormattedResponse(HttpStatus.CREATED.value(), true, created);
        return new HttpEntity<>(response);
    }

    @GetMapping("")
    public HttpEntity<?> getAllUsers() {
        Iterable<User> found = userService.findAllUsers();
        FormattedResponse response = new FormattedResponse(HttpStatus.OK.value(), true, found);
        return new HttpEntity<>(response);
    }


    @GetMapping("/{userID}")
    public ResponseEntity<?> getUserById(@PathVariable int userID) {
        User found = userService.findUserById(userID);
        System.out.println(found.getPassword());
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @PutMapping("/update_password")
    public HttpEntity<?> updateUserPassword(@RequestBody User user){
        // update user
        User updated = userService.saveOrUpdateUser(user);
        FormattedResponse response = new FormattedResponse(HttpStatus.OK.value(), true, updated);
        // send email to the user confirming the password update.

        return new HttpEntity<>(response);
    }
    @PutMapping("/update")
    public HttpEntity<?> updateUser(@RequestBody User user){

        User updated = userService.saveOrUpdateUser(user);
        FormattedResponse response = new FormattedResponse(HttpStatus.OK.value(), true, updated);
        return new HttpEntity<>(response);
    }
}