package com.dev.mcc_tools.controllers;

import com.dev.mcc_tools.domain.Order;
import com.dev.mcc_tools.domain.Profile;
import com.dev.mcc_tools.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.Format;
import java.util.HashMap;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;
//    @Autowired
//    private OrderSearch orderSearch;

//    private final OrderValidator orderValidator = new OrderValidator();

    @PostMapping("")
    public ResponseEntity<?> createNewOrder(@Valid @RequestBody Order order, BindingResult result) {
        HttpStatus httpStatus = HttpStatus.CREATED;

        Order created = orderService.saveOrUpdateOrder(order);
        FormattedResponse response = new FormattedResponse(httpStatus.value(), true, created);
        return new ResponseEntity<>(response, httpStatus);
    }

//    @GetMapping("")
//    public ResponseEntity<?> getAllProfiles() {
//        HttpStatus httpStatus = HttpStatus.OK;
//
//        Iterable<Profile> found = profileService.findAllProfiles();
//        FormattedResponse response = new FormattedResponse(httpStatus.value(), true, found);
//        return new ResponseEntity<>(response, httpStatus);
//    }

//    @GetMapping("/search")
//    public ResponseEntity<?> profileSearch(
//            @RequestParam(required = false, name = "firstName") String firstName,
//            @RequestParam(required = false, name = "lastName") String lastName,
//            @RequestParam(required = false, name = "phoneNumber") String phoneNumber,
//            @RequestParam(required = false, name = "email") String email
//    ) {
//        ProfileSearchRequest request = new ProfileSearchRequest();
//
//
//        if (firstName != null) request.setFirstName(firstName);
//        if (lastName != null) request.setLastName(lastName);
//        if (phoneNumber != null) request.setPhoneNumber(phoneNumber);
//        if (email != null) request.setEmail(email);
//
//        System.out.println(request.getFirstName());
//        System.out.println(request.getEmail());
//        HttpStatus httpStatus = HttpStatus.OK;
//        FormattedResponse response;
//
//        Iterable<Profile> found = profileSearch.findAllByCriteria(request);
//        response = new FormattedResponse(httpStatus.value(), true, found);
//        return new ResponseEntity<>(response, httpStatus);
//    }

//    @GetMapping("/{pk}")
//    public ResponseEntity<?> getProfileByID(@PathVariable int pk) {
//        HttpStatus httpStatus = HttpStatus.OK;
//
//        Profile found = profileService.findProfileById(pk);
//        FormattedResponse response = new FormattedResponse(httpStatus.value(), true, found);
//        return new ResponseEntity<>(response, httpStatus);
//    }

//    @GetMapping("/user/{userID}")
//    public ResponseEntity<?> getProfileByUserID(@PathVariable int userID) {
//        HttpStatus httpStatus = HttpStatus.OK;
//        FormattedResponse response;
//        HashMap<String, String> errors = profileValidator.getErrors();
//
//        Profile found = profileService.findProfileByUserID(userID);
//
//        profileValidator.checkProfile(found);
//
//        if (errors.isEmpty()) {
//
//            response = new FormattedResponse(httpStatus.value(), true, found);
//        } else {
//            httpStatus = HttpStatus.BAD_REQUEST;
//            response = new ErrorResponse(httpStatus.value(), false, errors);
//
//        }
//        return new ResponseEntity<>(response, httpStatus);
//    }


//    @PutMapping("/update/{pk}")
//    public ResponseEntity<?> updateProfile(@PathVariable int pk, @RequestBody Profile profile) {
//        HttpStatus httpStatus = HttpStatus.CREATED;
//        FormattedResponse response;
//        HashMap<String, String> errors = profileValidator.getErrors();
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