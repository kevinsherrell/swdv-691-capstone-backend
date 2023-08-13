package com.dev.mcc_tools.controllers;

import com.dev.mcc_tools.domain.Notification;
import com.dev.mcc_tools.services.NotificationService;
import com.dev.mcc_tools.validation.MccValidator;
import com.dev.mcc_tools.validation.NotificationValidator;
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
@RequestMapping("/notification")
@CrossOrigin
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("")
    public ResponseEntity<?> createNewNotification(@Valid @RequestBody Notification notification, BindingResult result) {
        HttpStatus httpStatus = HttpStatus.CREATED;

        Notification created = notificationService.saveOrUpdateNotification(notification);
        FormattedResponse response = new FormattedResponse(httpStatus.value(), true, created);
        // send an email whenever a notification is sent.
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllNotifications() {
        HttpStatus httpStatus = HttpStatus.OK;

        Iterable<Notification> found = notificationService.findAllNotifications();
        FormattedResponse response = new FormattedResponse(httpStatus.value(), true, found);
        return new ResponseEntity<>(response, httpStatus);
    }


    @GetMapping("/{notificationID}")
    public ResponseEntity<?> getProfileByID(@PathVariable int notificationID) {
        HttpStatus httpStatus = HttpStatus.OK;
        Notification found = notificationService.findNotificationByID(notificationID);
        FormattedResponse response = new FormattedResponse(httpStatus.value(), true, found);
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/profile/{profileID}")
    public ResponseEntity<?> getNotificationByProfileID(@PathVariable int profileID) {
        HttpStatus httpStatus = HttpStatus.OK;

        FormattedResponse response;

        NotificationValidator notificationValidator = new NotificationValidator();

        Iterable<Notification> found = notificationService.findNotificationsByProfileID(profileID);

        HashMap<String, String> errors = notificationValidator.checkNotification(found);


        if (errors.isEmpty()) {
            response = new FormattedResponse(httpStatus.value(), true, found);
        } else {
            response = new ErrorResponse(HttpStatus.BAD_REQUEST
                    .value(), false, errors);

        }
        return new ResponseEntity<>(response, httpStatus);

    }


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