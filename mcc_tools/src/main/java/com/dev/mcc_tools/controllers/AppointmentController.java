package com.dev.mcc_tools.controllers;

import com.dev.mcc_tools.domain.Appointment;
import com.dev.mcc_tools.domain.Order;
import com.dev.mcc_tools.search.AppointmentSearch;
import com.dev.mcc_tools.search.AppointmentSearchRequest;
import com.dev.mcc_tools.search.OrderSearchRequest;
import com.dev.mcc_tools.services.AppointmentService;
import com.dev.mcc_tools.validation.AppointmentValidator;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@RestController
@RequestMapping("/appointment")
@CrossOrigin
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private AppointmentSearch appointmentSearch;

    private final AppointmentValidator appointmentValidator = new AppointmentValidator();


    @GetMapping("/search")
    public ResponseEntity<?> AppointmentSearch(
            @RequestParam(required= false, name = "firstName") String firstName,
            @RequestParam(required = false, name = "lastName") String lastName,
            @RequestParam(required = false, name = "status") String status,
            @RequestParam(required = false, name = "minDate") String minDate,
            @RequestParam(required = false, name = "maxDate") String maxDate,
            @RequestParam(required = false, name = "invoiceNumber") String invoiceNumber,
            @RequestParam(required = false, name = "location") String location
    ) throws ParseException {
        HttpStatus httpStatus = HttpStatus.OK;
        FormattedResponse response;

        AppointmentSearchRequest request = new AppointmentSearchRequest();

        if(location != null) request.setLocation(location);
        if(firstName != null) request.setFirstName(firstName);
        if(lastName != null) request.setLastName(lastName);
        if (status != null) request.setStatus(status);
        if (minDate != null) request.setMinDate(minDate);
        if (maxDate != null) request.setMaxDate(maxDate);
        if (invoiceNumber != null) request.setInvoiceNumber(invoiceNumber);


        Iterable<Appointment> found = appointmentSearch.findAllByCriteria(request);
        response = new FormattedResponse(httpStatus.value(), true, found);
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping("")
    public ResponseEntity<?> createAppointment(@Valid @RequestBody Appointment appointment) {
        appointmentValidator.initializeErrors();

        HttpStatus httpStatus = HttpStatus.CREATED;
        FormattedResponse response;
        HashMap<String, String> errors = appointmentValidator.getErrors();

        // perform check here
        appointmentValidator.checkStatus(appointment.getStatus());

        if (errors.isEmpty()) {
            Appointment created = appointmentService.saveOrUpdateAppointment(appointment);
            response = new FormattedResponse(httpStatus.value(), true, created);
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
            response = new ErrorResponse(httpStatus.value(), false, errors);
        }

        return new ResponseEntity<>(response, httpStatus);

    }

    @GetMapping("")
    public ResponseEntity<?> getAllApointments() {
        HttpStatus httpStatus = HttpStatus.OK;

        Iterable<Appointment> found = appointmentService.findAllAppointments();
        FormattedResponse response = new FormattedResponse(httpStatus.value(), true, found);
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("{pk}")
    public ResponseEntity<?> getAppointmentByID(@PathVariable int pk) {
        appointmentValidator.initializeErrors();

        HttpStatus httpStatus = HttpStatus.OK;
        FormattedResponse response;

        HashMap<String, String> errors = appointmentValidator.getErrors();

        Appointment found = appointmentService.findByAppointmentID(pk);
        // check for null
        appointmentValidator.nullCheck("appointment", found);

        if (errors.isEmpty()) {
            response = new FormattedResponse(httpStatus.value(), true, found);
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
            response = new ErrorResponse(httpStatus.value(), false, errors);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/profile/{profileID}")
    public ResponseEntity<?> getAppointmentsByProfileID(@PathVariable int profileID) {
        HttpStatus httpStatus = HttpStatus.OK;
        FormattedResponse response;

        Iterable<Appointment> found = appointmentService.findByProfileID(profileID);
        response = new FormattedResponse(httpStatus.value(), true, found);

        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/order/{orderID}")
    public ResponseEntity<?> getAppointmentsByOrderID(@PathVariable int orderID) {
        HttpStatus httpStatus = HttpStatus.OK;
        FormattedResponse response;

        Iterable<Appointment> found = appointmentService.findByOrderID(orderID);
        response = new FormattedResponse(httpStatus.value(), true, found);

        return new ResponseEntity<>(response, httpStatus);
    }

    @PutMapping("/update/{pk}")
    public ResponseEntity<?> updateAppointment(@PathVariable int pk, @RequestBody Appointment appointment) {
        appointmentValidator.initializeErrors();

        HttpStatus httpStatus = HttpStatus.CREATED;
        FormattedResponse response;
        HashMap<String, String> errors = appointmentValidator.getErrors();

        Appointment found = appointmentService.findByAppointmentID(pk);

        // checks if the order id matches the id given in the path variable
        appointmentValidator.checkIDMatch(pk, appointment.getAppointmentID());
        // checks if the order exists in the database
        appointmentValidator.nullCheck("Appointment", found);
        // checks if the order fields are formatted correctly
        appointmentValidator.checkStatus(appointment.getStatus());


        if (errors.isEmpty()) {
            // protects these values from being changed on frontend
            appointment.setAppointmentID(found.getAppointmentID());
            appointment.setProfileID(found.getProfileID());
            appointment.setOrderID(found.getOrderID());
            appointment.setDate_created(found.getDate_created());


            if (!appointment.getDate().equals(found.getDate())) found.setDate(appointment.getDate());
            if (!appointment.getStatus().equals(found.getStatus())) found.setStatus(appointment.getStatus());
            if (!appointment.getNotes().equals(found.getNotes())) found.setNotes(appointment.getNotes());

            Appointment updated = appointmentService.saveOrUpdateAppointment(found);
            response = new FormattedResponse(httpStatus.value(), true, updated);
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
            response = new ErrorResponse(httpStatus.value(), false, errors);
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @PutMapping("/{pk}/update_status/{status}")
    public ResponseEntity<?> updateStatus(@PathVariable int pk, @PathVariable String status) {
        appointmentValidator.initializeErrors();

        HttpStatus httpStatus = HttpStatus.CREATED;
        FormattedResponse response;

        HashMap<String, String> errors = appointmentValidator.getErrors();

        Appointment found = appointmentService.findByAppointmentID(pk);

        appointmentValidator.checkStatus(status);
        appointmentValidator.nullCheck("Order", found);

        if (errors.isEmpty()) {
            found.setStatus(status);
            appointmentService.saveOrUpdateAppointment(found);
            response = new FormattedResponse(httpStatus.value(), true, found);
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
            response = new ErrorResponse(httpStatus.value(), false, errors);
        }
        return new ResponseEntity<>(response, httpStatus);
    }
}