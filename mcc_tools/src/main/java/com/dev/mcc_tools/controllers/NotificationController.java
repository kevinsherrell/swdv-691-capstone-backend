package com.dev.mcc_tools.controllers;

import com.dev.mcc_tools.domain.Notification;
import com.dev.mcc_tools.respositories.NotificationSearch;
import com.dev.mcc_tools.respositories.NotificationSearchRequest;
import com.dev.mcc_tools.services.NotificationService;
import com.dev.mcc_tools.validation.NotificationValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;

@RestController
@RequestMapping("/notification")
@CrossOrigin
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationSearch notificationSearch;
    private final NotificationValidator notificationValidator = new NotificationValidator();

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

    @GetMapping("/search")
    public ResponseEntity<?> notificationSearch(
            @RequestParam(required = false, name = "status") String status,
            @RequestParam(required = false, name = "profileID") Integer profileID,
            @RequestParam(required = false, name = "minDate") String minDate,
            @RequestParam(required = false, name = "maxDate") String maxDate,
            @RequestParam(required = false, name = "minTime") String minTime,
            @RequestParam(required = false, name = "maxTime") String maxTime
    ) throws ParseException {
        NotificationSearchRequest request = new NotificationSearchRequest();
        if (status != null) request.setStatus(status);
        if (profileID != null) request.setProfileID(profileID);
        if (minDate != null) request.setMinDate(minDate);
        if (maxDate != null) request.setMaxDate(maxDate);
        if (minTime != null) request.setMinTime(minTime);
        if (maxTime != null) request.setMaxTime(maxTime);

        HttpStatus httpStatus = HttpStatus.OK;

        System.out.println(request.getProfileID());
        Iterable<Notification> found = notificationSearch.findAllByCriteria(request);
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


    @PutMapping("/update/{pk}")
    public HttpEntity<?> updateNotification(@PathVariable int pk, @RequestBody Notification profile) {
        FormattedResponse response;

        Notification found = notificationService.findNotificationByID(pk);

        HashMap<String, String> errors = notificationValidator.checkNotification(found);

        if (errors.isEmpty()) {
            Notification updated = notificationService.saveOrUpdateNotification(profile);
            response = new FormattedResponse(HttpStatus.OK.value(), true, updated);
        } else {
            response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), false, errors);
        }

        return new HttpEntity<>(response);
    }


}