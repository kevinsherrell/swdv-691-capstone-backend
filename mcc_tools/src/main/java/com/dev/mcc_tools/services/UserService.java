package com.dev.mcc_tools.services;

import com.dev.mcc_tools.controllers.*;
import com.dev.mcc_tools.domain.User;
import com.dev.mcc_tools.respositories.UserRepository;
import com.dev.mcc_tools.validation.UserValidator;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.AuthProvider;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //    private final PasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UserValidator userValidator = new UserValidator();


    public FormattedResponse saveUser(User user) {
        userValidator.initializeErrors();

        HashMap<String, ArrayList<String>> errors = userValidator.getErrors();
        HttpStatus httpStatus = HttpStatus.CREATED;


        try {
            userValidator.checkEmail(user.getEmail());
            userValidator.checkPassword(user.getPassword());


            String unHashed = user.getPassword();

            user.setPassword(passwordEncoder.encode(unHashed));


            User updated = userRepository.save(user);

            System.out.println(errors.get("password"));
            return new FormattedResponse(httpStatus.value(), true, updated);

        } catch (DataIntegrityViolationException e) {
            httpStatus = HttpStatus.BAD_REQUEST;

            userValidator.setErrors("USER", "user already exists with this email.");

            return new ErrorResponse(httpStatus.value(), false, errors);
        } catch (IllegalArgumentException e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            return new FormattedResponse(httpStatus.value(), false, errors);
        }
    }

    public FormattedResponse updateUser(int id, User user) {
        userValidator.initializeErrors();

        HashMap<String, ArrayList<String>> errors = userValidator.getErrors();
        HttpStatus httpStatus = HttpStatus.CREATED;


        try {
            User found = userRepository.findById(id);

            userValidator.checkUserForUpdate(found);
            userValidator.checkIDMatch(user.getUserID(), found.getUserID());

//            if (errors.isEmpty()) {
            user.setPassword(found.getPassword());
            user.setDate_created(found.getDate_created());
            userRepository.save(user);
            return new FormattedResponse(httpStatus.value(), true, user);
//            }
        } catch (Exception e) {
            return new ErrorResponse(httpStatus.value(), false, errors);
        }
    }

    public FormattedResponse updatePassword(int id, Map<String, String> updateObj) {
        userValidator.initializeErrors();

        HashMap<String, ArrayList<String>> errors = userValidator.getErrors();
        HttpStatus httpStatus = HttpStatus.CREATED;

        User user = userRepository.findById(id);


        try {
            String oldPassword = user.getPassword();

            userValidator.checkUserForUpdate(user);
            userValidator.checkIDMatch(id, user.getUserID());
            userValidator.checkPasswordUpdate(oldPassword, updateObj);


            user.setPassword(passwordEncoder.encode(updateObj.get("newPassword")));
            userRepository.save(user);
            return new FormattedResponse(httpStatus.value(), true, user);

        } catch (Exception e) {
            return new ErrorResponse(httpStatus.value(), false, errors);

        }

    }

    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(int id) {

        User found = userRepository.findById(id);
        return found;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}