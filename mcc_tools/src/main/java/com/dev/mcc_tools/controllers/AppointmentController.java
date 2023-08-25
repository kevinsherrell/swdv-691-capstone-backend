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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.Normalizer;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
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
    private OrderSearchRequest searchRequest;

    @GetMapping("/search")
    public ResponseEntity<?> AppointmentSearch(
            @RequestParam(required = false, name = "status") String status,
            @RequestParam(required = false, name = "minDate") String minDate,
            @RequestParam(required = false, name = "maxDate") String maxDate,
            @RequestParam(required = false, name = "location") String location,
            @RequestParam(required = false, name = "minCreationDate") String minCreationDate,
            @RequestParam(required = false, name = "maxCreationDate") String maxCreationDate
    ) throws ParseException {
        HttpStatus httpStatus = HttpStatus.OK;
        FormattedResponse response;

        AppointmentSearchRequest request = new AppointmentSearchRequest();

        if (location != null) request.setLocation(location);

        if (status != null) request.setStatus(status);
        if (minDate != null) request.setMinDate(minDate);
        if (maxDate != null) request.setMaxDate(maxDate);
        if (minCreationDate != null) request.setMinCreationDate(minCreationDate);
        if (maxCreationDate != null) request.setMaxCreationDate(maxCreationDate);


        Iterable<Appointment> found = appointmentSearch.findAllByCriteria(request);
        response = new FormattedResponse(httpStatus.value(), true, found);
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/search/datesBetween")
    public ResponseEntity<?> findDatesBetween(
            @RequestParam(required = false, name = "date") String date,
            @RequestParam(required = false, name = "date2") String date2
    ) throws ParseException {

        AppointmentSearchRequest request = new AppointmentSearchRequest();

        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        if (date != null) request.setMinDate(date);
        if (date2 != null) request.setMaxDate(date2);


        FormattedResponse response = appointmentService.findByDatesBetween(request.parseDateString(request.getMinDate()), request.parseDateString(request.getMaxDate()));


        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/search/createdBetween")
    public ResponseEntity<?> findCreatedBetween(
            @RequestParam(required = false, name = "date") String date,
            @RequestParam(required = false, name = "date2") String date2
    ) throws ParseException {

        AppointmentSearchRequest request = new AppointmentSearchRequest();

        if (date != null) request.setMinDate(date);
        if (date2 != null) request.setMaxDate(date2);


        FormattedResponse response = appointmentService.findByCreatedDatesBetween(request.parseDateString(request.getMinDate()), request.parseDateString(request.getMaxDate()));


        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PostMapping("")
    public ResponseEntity<?> createAppointment(@Valid @RequestBody Appointment appointment) {

        FormattedResponse response = appointmentService.saveAppointment(appointment);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));

    }

    @GetMapping("")
    public ResponseEntity<?> getAllApointments() {
        FormattedResponse response = appointmentService.findAllAppointments();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("{pk}")
    public ResponseEntity<?> getAppointmentByID(@PathVariable int pk) throws Exception {

        FormattedResponse response = appointmentService.findByAppointmentID(pk);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/profile/{profileID}")
    public ResponseEntity<?> getAppointmentsByProfileID(@PathVariable int profileID) {


        FormattedResponse response = appointmentService.findByProfileID(profileID);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/order/{orderID}")
    public ResponseEntity<?> getAppointmentsByOrderID(@PathVariable int orderID) {

        FormattedResponse response = appointmentService.findByOrderID(orderID);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PutMapping("/update/{pk}")
    public ResponseEntity<?> updateAppointment(@PathVariable int pk, @RequestBody Appointment appointment) throws Exception {

        FormattedResponse response = appointmentService.updateAppointment(pk, appointment);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

//    @PutMapping("/{pk}/update_status/{status}")
//    public ResponseEntity<?> updateStatus(@PathVariable int pk, @PathVariable String status) throws Exception {
//        appointmentValidator.initializeErrors();
//
//        HttpStatus httpStatus = HttpStatus.CREATED;
//        FormattedResponse response;
//
//        HashMap<String, ArrayList<String>> errors = appointmentValidator.getErrors();
////        HashMap<String, String> errors = appointmentValidator.getErrors();
//
//        Appointment found = appointmentService.findByAppointmentID(pk);
//
//        appointmentValidator.checkStatus(status);
//        appointmentValidator.nullCheck("Order", found);
//
//        if (errors.isEmpty()) {
//            found.setStatus(status);
//            appointmentService.saveOrUpdateAppointment(found);
//            response = new FormattedResponse(httpStatus.value(), true, found);
//        } else {
//            httpStatus = HttpStatus.BAD_REQUEST;
//            response = new ErrorResponse(httpStatus.value(), false, errors);
//        }
//        return new ResponseEntity<>(response, httpStatus);
//    }
}