package com.dev.mcc_tools.controllers;

import com.dev.mcc_tools.domain.Profile;
import com.dev.mcc_tools.domain.User;
import com.dev.mcc_tools.search.ProfileSearch;
import com.dev.mcc_tools.search.ProfileSearchRequest;
import com.dev.mcc_tools.search.UserSearchRequest;
import com.dev.mcc_tools.services.ProfileService;
import com.dev.mcc_tools.validation.MccValidator;
import com.dev.mcc_tools.validation.ProfileValidator;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.text.Format;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/profile")
@CrossOrigin
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ProfileSearch profileSearch;
    private final ProfileValidator profileValidator = new ProfileValidator();

    @PostMapping("")
    public ResponseEntity<?> createNewProfile(@Valid @RequestBody Profile profile, BindingResult result) {

        FormattedResponse response = profileService.saveProfile(profile);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
///    @PostMapping("")
//    public ResponseEntity<?> createNewProfile(@Valid @RequestBody Profile profile, BindingResult result) {
//        HttpStatus httpStatus = HttpStatus.CREATED;
//
//        Profile created = profileService.saveOrUpdateProfile(profile);
//        FormattedResponse response = new FormattedResponse(httpStatus.value(), true, created);
//        return new ResponseEntity<>(response, httpStatus);
//    }

    @GetMapping("")
    public ResponseEntity<?> getAllProfiles() {
        HttpStatus httpStatus = HttpStatus.OK;

        Iterable<Profile> found = profileService.findAllProfiles();
        FormattedResponse response = new FormattedResponse(httpStatus.value(), true, found);
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/search")
    public ResponseEntity<?> profileSearch(
            @RequestParam(required = false, name = "firstName") String firstName,
            @RequestParam(required = false, name = "lastName") String lastName,
            @RequestParam(required = false, name = "phoneNumber") String phoneNumber,
            @RequestParam(required = false, name = "email") String email
    ) throws ParseException {
        ProfileSearchRequest request = new ProfileSearchRequest();


        if (firstName != null) request.setFirstName(firstName);
        if (lastName != null) request.setLastName(lastName);
        if (phoneNumber != null) request.setPhoneNumber(phoneNumber);
        if (email != null) request.setEmail(email);

        System.out.println(request.getFirstName());
        System.out.println(request.getEmail());
        HttpStatus httpStatus = HttpStatus.OK;
        FormattedResponse response;

        Iterable<Profile> found = profileSearch.findAllByCriteria(request);
        response = new FormattedResponse(httpStatus.value(), true, found);
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/{pk}")
    public ResponseEntity<?> getProfileByID(@PathVariable int pk) {


        FormattedResponse response = profileService.findProfileById(pk);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<?> getProfileByUserID(@PathVariable int userID) {
      FormattedResponse response = profileService.findProfileByUserID(userID);
      return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }


//    @PutMapping("/update/{pk}")
//    public ResponseEntity<?> updateProfile(@PathVariable int pk, @RequestBody Profile profile) {
//        HttpStatus httpStatus = HttpStatus.CREATED;
//        FormattedResponse response;
//        HashMap<String, ArrayList<String>> errors = profileValidator.getErrors();
////        HashMap<String, String> errors = profileValidator.getErrors();
//
//        Profile found = profileService.findProfileById(pk);
//
//        profileValidator.checkProfile(found);
//
//        if (errors.isEmpty()) {
//            Profile updated = profileService.saveOrUpdateProfile(profile);
//            response = new FormattedResponse(httpStatus.value(), true, updated);
//        } else {
//            httpStatus = HttpStatus.BAD_REQUEST;
//            response = new ErrorResponse(httpStatus.value(), false, errors);
//        }
//
//        return new ResponseEntity<>(response, httpStatus);
//    }
}