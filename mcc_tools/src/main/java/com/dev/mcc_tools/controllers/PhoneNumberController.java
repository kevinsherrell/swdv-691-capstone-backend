package com.dev.mcc_tools.controllers;

import com.dev.mcc_tools.domain.PhoneNumber;
import com.dev.mcc_tools.services.PhoneNumberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/phone")
@CrossOrigin
public class PhoneNumberController {
    @Autowired
    private PhoneNumberService phoneNumberService;

    @PostMapping("")
    public HttpEntity<?> createNewPhoneNumber(@Valid @RequestBody PhoneNumber phoneNumber, BindingResult result) {
        PhoneNumber created = phoneNumberService.saveOrUpdatePhoneNumber(phoneNumber);
        FormattedResponse response = new FormattedResponse(HttpStatus.CREATED.value(), true, created);
        return new HttpEntity<>(response);
    }

//    @GetMapping("")
//    public HttpEntity<?> getAllProfiles() {
//        Iterable<Profile> found = profileService.findAllProfiles();
//        FormattedResponse response = new FormattedResponse(HttpStatus.OK.value(), true, found);
//        return new HttpEntity<>(response);
//    }


//    @GetMapping("/{profileID}")
//    public ResponseEntity<?> getProfileByID(@PathVariable int profileID) {
//        Profile found = profileService.findProfileById(profileID);
//        return new ResponseEntity<>(found, HttpStatus.OK);
//    }

//    @GetMapping("/user/{userID}")
//    public HttpEntity<?> getProfileByUserID(@PathVariable int userID) {
//        FormattedResponse response;
//        MccValidator validator = new MccValidator();
//        Profile found = profileService.findProfileByUserID(userID);
//        System.out.println(found);
//        HashMap<String, String> errors = validator.checkProfile(found);
//
//
//        if (found != null) {
//            response = new FormattedResponse(HttpStatus.OK.value(), true, found);
//        } else {
//            response = new ErrorResponse(HttpStatus.BAD_REQUEST
//                    .value(), false, errors);
//
//        }
//        return new HttpEntity<>(response);
//
//    }


//    @PutMapping("/update/{pk}")
//    public HttpEntity<?> updateProfile(@PathVariable int pk, @RequestBody Profile profile) {
//        FormattedResponse response;
//
//        MccValidator validator = new MccValidator();
//
//        Profile found = profileService.findProfileById(pk);
//
//        HashMap<String, String> errors = validator.checkProfile(found);
//
//        if (errors.isEmpty()) {
//            Profile updated = profileService.saveOrUpdateProfile(profile);
//            response = new FormattedResponse(HttpStatus.OK.value(), true, updated);
//        } else {
//            response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), false, errors);
//        }
//
//        return new HttpEntity<>(response);
//    }
}