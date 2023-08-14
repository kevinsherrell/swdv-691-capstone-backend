package com.dev.mcc_tools.controllers;

import com.dev.mcc_tools.domain.Profile;
import com.dev.mcc_tools.services.ProfileService;
import com.dev.mcc_tools.validation.MccValidator;
import com.dev.mcc_tools.validation.ProfileValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.Format;
import java.util.HashMap;

@RestController
@RequestMapping("/profile")
@CrossOrigin
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    private final ProfileValidator profileValidator = new ProfileValidator();
    @PostMapping("")
    public ResponseEntity<?> createNewProfile(@Valid @RequestBody Profile profile, BindingResult result) {
        HttpStatus httpStatus = HttpStatus.CREATED;

        Profile created = profileService.saveOrUpdateProfile(profile);
        FormattedResponse response = new FormattedResponse(httpStatus.value(), true, created);
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllProfiles() {
        HttpStatus httpStatus = HttpStatus.OK;

        Iterable<Profile> found = profileService.findAllProfiles();
        FormattedResponse response = new FormattedResponse(httpStatus.value(), true, found);
        return new ResponseEntity<>(response, httpStatus);
    }


    @GetMapping("/{pk}")
    public ResponseEntity<?> getProfileByID(@PathVariable int pk) {
        HttpStatus httpStatus = HttpStatus.OK;

        Profile found = profileService.findProfileById(pk);
        FormattedResponse response = new FormattedResponse(httpStatus.value(), true, found);
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<?> getProfileByUserID(@PathVariable int userID) {
        HttpStatus httpStatus = HttpStatus.OK;
        FormattedResponse response;
        HashMap<String, String> errors = profileValidator.getErrors();

        Profile found = profileService.findProfileByUserID(userID);

        profileValidator.checkProfile(found);

        if (errors.isEmpty()) {

            response = new FormattedResponse(httpStatus.value(), true, found);
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
            response = new ErrorResponse(httpStatus.value(), false, errors);

        }
        return new ResponseEntity<>(response, httpStatus);
    }


    @PutMapping("/update/{pk}")
    public ResponseEntity<?> updateProfile(@PathVariable int pk, @RequestBody Profile profile) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        FormattedResponse response;
        HashMap<String, String> errors = profileValidator.getErrors();

        Profile found = profileService.findProfileById(pk);

        profileValidator.checkProfile(found);

        if (errors.isEmpty()) {
            Profile updated = profileService.saveOrUpdateProfile(profile);
            response = new FormattedResponse(httpStatus.value(), true, updated);
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
            response = new ErrorResponse(httpStatus.value(), false, errors);
        }

        return new ResponseEntity<>(response, httpStatus);
    }
}