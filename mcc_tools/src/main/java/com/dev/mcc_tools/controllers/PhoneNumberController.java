package com.dev.mcc_tools.controllers;

import com.dev.mcc_tools.domain.PhoneNumber;
import com.dev.mcc_tools.services.PhoneNumberService;
import com.dev.mcc_tools.validation.MccValidator;
import com.sun.net.httpserver.HttpsServer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/phone")
@CrossOrigin
public class PhoneNumberController {
    @Autowired
    private PhoneNumberService phoneNumberService;

    @PostMapping("")
    public HttpEntity<?> createNewPhoneNumber(@Valid @RequestBody PhoneNumber phoneNumber, BindingResult result) {
        int primaryCount = phoneNumberService.countPrimaryPhoneNumbers();
        if(primaryCount < 1){
            phoneNumber.setIsPrimary(true);
        }

        PhoneNumber created = phoneNumberService.saveOrUpdatePhoneNumber(phoneNumber);
        FormattedResponse response = new FormattedResponse(HttpStatus.CREATED.value(), true, created);

        // select count from phone_numbers where isPrimary = true. if the number is less than 1, set create account to primary
        return new HttpEntity<>(response);
    }

    @GetMapping("")
    public HttpEntity<?> getAllPhoneNumbers() {
        Iterable<PhoneNumber> found = phoneNumberService.findAllPhoneNumbers();
        FormattedResponse response = new FormattedResponse(HttpStatus.OK.value(), true, found);
        return new HttpEntity<>(response);
    }


    @GetMapping("/{phoneID}")
    public HttpEntity<?> getPhoneNumberByID(@PathVariable int phoneID) {
        PhoneNumber found = phoneNumberService.findPhoneNumberByID(phoneID);
        return new HttpEntity<>(found);
    }

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
    @PutMapping("/{pk}/makePrimary")
    public HttpEntity<?> makePrimary(@PathVariable int pk, @RequestBody HashMap<String, Boolean> body) {
        PhoneNumber found = phoneNumberService.findPhoneNumberByID(pk);
        System.out.println(found.toString());
        FormattedResponse response;
        MccValidator validator =  new MccValidator();
        HashMap<String, String> errors = validator.checkPhoneNumber(found);

//
        if (errors.isEmpty()) {
            found.setIsPrimary(body.get("isPrimary"));
            PhoneNumber saved = phoneNumberService.saveOrUpdatePhoneNumber(found);
            response = new FormattedResponse(HttpStatus.CREATED.value(), true, saved);

            // set all user phone numbers isPrimary to false
            Iterable<PhoneNumber> numbers = phoneNumberService.findPhoneNumbersByProfileID(found.getProfileID());
            for(PhoneNumber number :  numbers){
                if(number.getPhoneID() != found.getPhoneID()){
                    number.setIsPrimary(false);
                }
            }
            phoneNumberService.saveAllPhoneNumbers(numbers);
        }else{
            response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), false, errors);
        }



        return new HttpEntity<>(response);
    }
}