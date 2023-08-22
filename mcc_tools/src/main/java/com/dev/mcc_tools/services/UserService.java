package com.dev.mcc_tools.services;

import com.dev.mcc_tools.controllers.*;
import com.dev.mcc_tools.domain.Profile;
import com.dev.mcc_tools.domain.Role;
import com.dev.mcc_tools.domain.User;
import com.dev.mcc_tools.respositories.ProfileRepository;
import com.dev.mcc_tools.respositories.UserRepository;
import com.dev.mcc_tools.validation.ProfileValidator;
import com.dev.mcc_tools.validation.UserValidator;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.parameters.P;
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
    private ProfileRepository profileRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //    private final PasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UserValidator userValidator = new UserValidator();
    private final ProfileValidator profileValidator = new ProfileValidator();

    public FormattedResponse saveUser(RegistrationRequest request) {
        userValidator.initializeErrors();
        profileValidator.initializeErrors();

        HashMap<String, ArrayList<String>> errors = userValidator.getErrors();
        HashMap<String, ArrayList<String>> profileErrors = profileValidator.getErrors();
        HashMap<String, ArrayList<String>> combinedErrors = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.CREATED;

        try {

            userValidator.checkEmail(request.getEmail());
            userValidator.checkPasswordFormat(request.getPassword());
            userValidator.checkPasswordRegistration(request.getPassword(), request.getVerifyPassword());

            profileValidator.checkNullOrEmpty(
                    request.getFirstName(),
                    request.getLastName(),
                    request.getAddress1(),
                    request.getCity(),
                    request.getState(),
                    request.getZipCode(),
                    request.getPhoneNumber(),
                    request.getPhoneType()
            );

            profileValidator.checkFormat(
                    request.getState(),
                    request.getZipCode(),
                    request.getPhoneNumber(),
                    request.getPhoneType()
            );
            User found = userRepository.findByEmail(request.getEmail());
            userValidator.checkExistsForCreation(found);

            User newUser = new User();

            newUser.setEmail(request.getEmail());

            newUser.setPassword(passwordEncoder.encode(request.getPassword()));

            newUser.setRole(Role.valueOf(request.getRole()));

            User createdUser = userRepository.save(newUser);

            Profile newProfile = new Profile();
            newProfile.setFirstName(request.getFirstName());
            newProfile.setMiddleInitial(request.getMiddleInitial());
            newProfile.setLastName(request.getLastName());
            newProfile.setAddress1(request.getAddress1());
            newProfile.setAddress2(request.getAddress2());
            newProfile.setCity(request.getCity());
            newProfile.setState(request.getState());
            newProfile.setZipCode(request.getZipCode());
            newProfile.setPhoneNumber(request.getPhoneNumber());
            newProfile.setPhoneType(request.getPhoneType());
            newProfile.setUserID(createdUser.getUserID());

            profileRepository.save(newProfile);
            return new FormattedResponse(httpStatus.value(), true, createdUser);
        } catch (Exception e) {
            e.printStackTrace();
            httpStatus = HttpStatus.BAD_REQUEST;

            // combine profile and user errors
            combinedErrors.putAll(errors);
            combinedErrors.putAll(profileErrors);

            return new ErrorResponse(httpStatus.value(), false, combinedErrors);
        }

//        try {
//            userValidator.checkEmail(userAndProfile.get("user").getEmail());
//            userValidator.checkPassword(user.getPassword());
//
//
//            String unHashed = user.getPassword();
//
//            user.setPassword(passwordEncoder.encode(unHashed));
//
//
//            User updated = userRepository.save(user);
//
//            System.out.println(errors.get("password"));
//            return new FormattedResponse(httpStatus.value(), true, updated);
//
//        } catch (DataIntegrityViolationException e) {
//            httpStatus = HttpStatus.BAD_REQUEST;
//
//            userValidator.setErrors("USER", "user already exists with this email.");
//
//            return new ErrorResponse(httpStatus.value(), false, errors);
//        } catch (IllegalArgumentException e) {
//            httpStatus = HttpStatus.BAD_REQUEST;
//            return new FormattedResponse(httpStatus.value(), false, errors);
//        }
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