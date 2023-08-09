package com.dev.mcc_tools.controllers;

import com.dev.mcc_tools.domain.Profile;
import com.dev.mcc_tools.domain.User;
import com.dev.mcc_tools.services.ProfileService;
import com.dev.mcc_tools.services.UserService;
import com.dev.mcc_tools.validation.MccValidator;
import jakarta.validation.Valid;
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
@RequestMapping("/profile")
@CrossOrigin
public class ProfileController {
    @Autowired
    private ProfileService profileService;

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
    public ResponseEntity<?> getUserById(@PathVariable int profileID) {
        Profile found = profileService.findProfileById(profileID);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

//    @PutMapping("/{pk}/update_password")
//    public HttpEntity<?> updateUserPassword(@PathVariable int pk, @RequestBody Map<String, String> updateObj) {
//        FormattedResponse response;
//        MccValidator validator = new MccValidator();
//        // get user by id
//        User user = userService.findUserById(pk);
//        // get hashed user password
//        String oldPassword = user.getPassword();
//
//        // compare old password to new password
//
//        // check if new password is the same as the old password
//        HashMap<String, String> errors = validator.checkPasswordUpdate(oldPassword, updateObj);
//
//        // hash the old password
//        // set the found user password to the new password
//        if (errors == null) {
//            System.out.println("No Errors");
//            user.setPassword(updateObj.get("newPassword"));
//            // save the user
//            userService.saveOrUpdateUser(user);
//            // send and email to the user confirming that the password has been changed.
//
//            response = new FormattedResponse(HttpStatus.OK.value(), true, user);
//        } else {
//            System.out.println("Errors present");
//            response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), false, errors);
//        }
//
//        return new HttpEntity<>(response);
//
//    }

//    @PutMapping("/update")
//    public HttpEntity<?> updateUser(@RequestBody User user) {
//
//        User updated = userService.saveOrUpdateUser(user);
//        FormattedResponse response = new FormattedResponse(HttpStatus.OK.value(), true, updated);
//        return new HttpEntity<>(response);
//    }
}