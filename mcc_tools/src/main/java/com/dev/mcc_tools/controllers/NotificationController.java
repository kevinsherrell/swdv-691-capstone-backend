package com.dev.mcc_tools.controllers;

import com.dev.mcc_tools.domain.Notification;
import com.dev.mcc_tools.services.NotificationService;
import com.dev.mcc_tools.validation.MccValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
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
    public HttpEntity<?> createNewNotification(@Valid @RequestBody Notification notification, BindingResult result) {
        System.out.println(notification.toString());
//        Notification nt = new Notification("notification header", "notification body", "unread", 1);
        // send an email whenever a notification is sent.
        Notification created = notificationService.saveOrUpdateNotification(notification);
        FormattedResponse response = new FormattedResponse(HttpStatus.CREATED.value(), true, created);
        return new HttpEntity<>(response);
    }

    @GetMapping("")
    public HttpEntity<?> getAllNotifications() {
        Iterable<Notification> found = notificationService.findAllNotifications();
        FormattedResponse response = new FormattedResponse(HttpStatus.OK.value(), true, found);
        return new HttpEntity<>(response);
    }


    @GetMapping("/{notificationID}")
    public HttpEntity<?> getProfileByID(@PathVariable int notificationID) {
        Notification found = notificationService.findNotificationByID(notificationID);
        FormattedResponse response = new FormattedResponse(HttpStatus.OK.value(), true, found);
        return new HttpEntity<>(response);
    }

    @GetMapping("/profile/{profileID}")
    public HttpEntity<?> getNotificationByProfileID(@PathVariable int profileID) {
        FormattedResponse response;

        MccValidator validator = new MccValidator();

        Iterable<Notification> found = notificationService.findNotificationsByProfileID(profileID);

        HashMap<String, String> errors = validator.checkNotification(found);


        if (errors.isEmpty()) {
            response = new FormattedResponse(HttpStatus.OK.value(), true, found);
        } else {
            response = new ErrorResponse(HttpStatus.BAD_REQUEST
                    .value(), false, errors);

        }
        return new HttpEntity<>(response);

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