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
import java.util.HashMap;

@RestController
@RequestMapping("/profile")
@CrossOrigin
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    private final ProfileValidator validator = new ProfileValidator();
    @PostMapping("")
    public HttpEntity<?> createNewProfile(@Valid @RequestBody Profile profile, BindingResult result) {
        Profile created = profileService.saveOrUpdateProfile(profile);
        FormattedResponse response = new FormattedResponse(HttpStatus.CREATED.value(), true, created);
        return new HttpEntity<>(response);
    }

    @GetMapping("")
    public HttpEntity<?> getAllProfiles() {
        Iterable<Profile> found = profileService.findAllProfiles();
        FormattedResponse response = new FormattedResponse(HttpStatus.OK.value(), true, found);
        return new HttpEntity<>(response);
    }


    @GetMapping("/{profileID}")
    public ResponseEntity<?> getProfileByID(@PathVariable int profileID) {
        Profile found = profileService.findProfileById(profileID);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @GetMapping("/user/{userID}")
    public HttpEntity<?> getProfileByUserID(@PathVariable int userID) {
        FormattedResponse response;
//        ProfileValidator validator = new ProfileValidator();
        Profile found = profileService.findProfileByUserID(userID);
        System.out.println(found);
        HashMap<String, String> errors = this.validator.checkProfile(found);


        if (found != null) {
            response = new FormattedResponse(HttpStatus.OK.value(), true, found);
        } else {
            response = new ErrorResponse(HttpStatus.BAD_REQUEST
                    .value(), false, errors);

        }
        return new HttpEntity<>(response);

    }


    @PutMapping("/update/{pk}")
    public HttpEntity<?> updateProfile(@PathVariable int pk, @RequestBody Profile profile) {
        FormattedResponse response;

//        ProfileValidator validator = new ProfileValidator();
        Profile found = profileService.findProfileById(pk);

        this.validator.checkProfile(found);

        HashMap<String, String> errors = this.validator.getErrors();

        if (errors.isEmpty()) {
            Profile updated = profileService.saveOrUpdateProfile(profile);
            response = new FormattedResponse(HttpStatus.OK.value(), true, updated);
        } else {
            response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), false, errors);
        }

        return new HttpEntity<>(response);
    }
}