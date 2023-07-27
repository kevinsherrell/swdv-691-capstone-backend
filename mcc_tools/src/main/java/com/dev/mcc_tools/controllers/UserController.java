package com.dev.mcc_tools.controllers;

import com.dev.mcc_tools.domain.User;
import com.dev.mcc_tools.services.UserService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody User user, BindingResult result) {
            User created = userService.saveOrUpdateUser(user);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("")
    public Iterable<User> getAllUsers() {
        return userService.findAllUsers();
    }
    @GetMapping("/{userID}")
    public ResponseEntity<?> getUserById(@PathVariable int userID){
        User found = userService.findUserById(userID);
        return new ResponseEntity<>(found, HttpStatus.FOUND);
    }
}