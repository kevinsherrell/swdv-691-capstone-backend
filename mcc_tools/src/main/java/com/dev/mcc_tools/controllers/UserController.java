package com.dev.mcc_tools.controllers;

import com.dev.mcc_tools.domain.User;
import com.dev.mcc_tools.services.UserService;
import com.dev.mcc_tools.validation.MccValidator;
import com.dev.mcc_tools.validation.UserValidator;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.Format;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    private final UserValidator userValidator = new UserValidator();

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

    @PutMapping("/{pk}/update_password")
    public ResponseEntity<?> updateUserPassword(@PathVariable int pk, @RequestBody Map<String, String> updateObj) {
        userValidator.initializeErrors();
        HttpStatus httpStatus = null;
        FormattedResponse response;

        // get user by id
        User user = userService.findUserById(pk);
        // get hashed user password
        String oldPassword = user.getPassword();

        // compare old password to new password
        userValidator.checkPasswordUpdate(oldPassword, updateObj);
        // check if new password is the same as the old password
        HashMap<String, String> errors = userValidator.getErrors();

        // hash the old password
        // set the found user password to the new password
        if (errors.isEmpty()) {
            httpStatus = HttpStatus.OK;
            System.out.println("No Errors");
            user.setPassword(updateObj.get("newPassword"));
            // save the user
            userService.saveOrUpdateUser(user);
            // send and email to the user confirming that the password has been changed.

            response = new FormattedResponse(httpStatus.value(), true, user);
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
            System.out.println("Errors present");
            response = new ErrorResponse(httpStatus.value(), false, errors);
        }
        System.out.println(user.getPassword());
        return new ResponseEntity<>(response, httpStatus);

    }

    @PutMapping("/update/{pk}")
    public ResponseEntity<?> updateUser(@PathVariable int pk, @RequestBody User user) {
        userValidator.initializeErrors();

        FormattedResponse response;

        HttpStatus httpStatus;

        HashMap<String, String> errors = userValidator.getErrors();

        User found = userService.findUserById(pk);


        userValidator.checkUserForUpdate(found);

        if(errors.isEmpty()){
            httpStatus  = HttpStatus.OK;
            user.setPassword(found.getPassword());
            user.setDate_created(found.getDate_created());
            User updated = userService.saveOrUpdateUser(user);
            response = new FormattedResponse(httpStatus.value(), true, updated);
        }else{
            httpStatus = HttpStatus.BAD_REQUEST;
            response = new ErrorResponse(httpStatus.value(), false, errors);
        }


        return new ResponseEntity<>(response, httpStatus);
    }

}