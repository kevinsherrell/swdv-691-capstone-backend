//package com.dev.mcc_tools.controllers;
//
//import com.dev.mcc_tools.domain.PhoneNumber;
//import com.dev.mcc_tools.services.PhoneNumberService;
//import com.dev.mcc_tools.validation.MccValidator;
//import com.dev.mcc_tools.validation.PhoneNumberValidator;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpStatus;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//@RestController
//@RequestMapping("/phone")
//@CrossOrigin
//public class PhoneNumberController {
//    @Autowired
//    private PhoneNumberService phoneNumberService;
//
//    @PostMapping("")
//    public HttpEntity<?> createNewPhoneNumber(@Valid @RequestBody PhoneNumber phoneNumber, BindingResult result) {
//
//        PhoneNumber created = phoneNumberService.saveOrUpdatePhoneNumber(phoneNumber);
//        FormattedResponse response = new FormattedResponse(HttpStatus.CREATED.value(), true, created);
//
//        return new HttpEntity<>(response);
//    }
//
//    @GetMapping("")
//    public HttpEntity<?> getAllPhoneNumbers() {
//        Iterable<PhoneNumber> found = phoneNumberService.findAllPhoneNumbers();
//        FormattedResponse response = new FormattedResponse(HttpStatus.OK.value(), true, found);
//        return new HttpEntity<>(response);
//    }
//
//    @GetMapping("/{phoneID}")
//    public HttpEntity<?> getPhoneNumberByID(@PathVariable int phoneID) {
//        PhoneNumber found = phoneNumberService.findPhoneNumberByID(phoneID);
//        return new HttpEntity<>(found);
//    }
//
//    @PutMapping("/update/{pk}")
//    public HttpEntity<?> updatePhoneNumber(@PathVariable int pk, @RequestBody PhoneNumber phoneNumber) {
//        FormattedResponse response;
//
//        PhoneNumberValidator validator = new PhoneNumberValidator();
//
//        PhoneNumber found = phoneNumberService.findPhoneNumberByID(pk);
//
////        HashMap<String, String> errors = validator.checkPhoneNumber(found);
//        HashMap<String, ArrayList<String>> errors = validator.getErrors();
//
//        validator.checkPhoneNumber(found);
//
//        if (errors.isEmpty()) {
//            PhoneNumber updated = phoneNumberService.saveOrUpdatePhoneNumber(phoneNumber);
//            response = new FormattedResponse(HttpStatus.OK.value(), true, updated);
//        } else {
//            response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), false, errors);
//        }
//
//        return new HttpEntity<>(response);
//    }
//
//
////    @PutMapping("/{pk}/makePrimary")
////    public HttpEntity<?> makePrimary(@PathVariable int pk) {
////        PhoneNumber found = phoneNumberService.findPhoneNumberByID(pk);
////        System.out.println(found.toString());
////        FormattedResponse response;
////        PhoneNumberValidator validator =  new PhoneNumberValidator();
////        HashMap<String, String> errors = validator.checkPhoneNumber(found);
////
//////
////        if (errors.isEmpty()) {
////            PhoneNumber saved = phoneNumberService.saveOrUpdatePhoneNumber(found);
////            response = new FormattedResponse(HttpStatus.CREATED.value(), true, saved);
////
////            // set all user phone numbers isPrimary to false
////            Iterable<PhoneNumber> numbers = phoneNumberService.findPhoneNumbersByProfileID(found.getProfileID());
////            phoneNumberService.saveAllPhoneNumbers(numbers);
////        }else{
////            response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), false, errors);
////        }
////
////
////
////        return new HttpEntity<>(response);
////    }
//}