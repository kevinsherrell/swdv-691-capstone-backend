package com.dev.mcc_tools.controllers;

import com.dev.mcc_tools.domain.Order;
import com.dev.mcc_tools.domain.Profile;
import com.dev.mcc_tools.search.OrderSearch;
import com.dev.mcc_tools.search.OrderSearchRequest;
import com.dev.mcc_tools.services.OrderService;
import com.dev.mcc_tools.validation.OrderValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.Format;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderSearch orderSearch;

    private final OrderValidator orderValidator = new OrderValidator();

    @PostMapping("")
    public ResponseEntity<?> createNewOrder(@RequestBody Order order) {

        FormattedResponse response = orderService.saveOrder(order);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
//        orderValidator.initializeErrors();
//
//
//        HttpStatus httpStatus = HttpStatus.CREATED;
//        FormattedResponse response;
//        HashMap<String, String> errors = orderValidator.getErrors();
//
//        orderValidator.checkOrderFormat(order);
//
//        if (errors.isEmpty()) {
//            Order created = orderService.saveOrUpdateOrder(order);
//            response = new FormattedResponse(httpStatus.value(), true, created);
//        } else {
//            httpStatus = HttpStatus.BAD_REQUEST;
//            response = new ErrorResponse(httpStatus.value(), false, errors);
//        }
//        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllOrders() {

        FormattedResponse response = orderService.findAllOrders();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

//    @GetMapping("/search")
//    public ResponseEntity<?> OrderSearch(
//            @RequestParam(required = false, name = "invoiceNumber") String invoiceNumber,
//            @RequestParam(required = false, name = "status") String status,
////            @RequestParam(required = false, name = "firstName") String firstName,
////            @RequestParam(required = false, name = "lastName") String lastName,
////            @RequestParam(required = false, name = "email") String email,
//            @RequestParam(required = false, name = "minDate") String minDate,
//            @RequestParam(required = false, name = "maxDate") String maxDate
//    ) throws ParseException {
//        HttpStatus httpStatus = HttpStatus.OK;
//        FormattedResponse response;
//
//        OrderSearchRequest request = new OrderSearchRequest();
//
//
//        if (invoiceNumber != null) request.setInvoiceNumber(invoiceNumber);
//        if (status != null) request.setStatus(status);
////        if (firstName != null) request.setFirstName(firstName);
////        if (lastName != null) request.setLastName(lastName);
////        if (email != null) request.setEmail(email);
//        if (minDate != null) request.setMinDate(minDate);
//        if (maxDate != null) request.setMaxDate(maxDate);
//
//
//        Iterable<Order> found = orderSearch.findAllByCriteria(request);
//        response = new FormattedResponse(httpStatus.value(), true, found);
//        return new ResponseEntity<>(response, httpStatus);
//    }

    @GetMapping("/{pk}")
    public ResponseEntity<?> getOrderByID(@PathVariable int pk) {

        FormattedResponse response = orderService.findOrderById(pk);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/profile/{profileID}")
    public ResponseEntity<?> getOrderByProfileID(@PathVariable int profileID) {
        FormattedResponse response = orderService.findAllByProfileID(profileID);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }


    @PutMapping("/update/{pk}")
    public ResponseEntity<?> updateOrder(@PathVariable int pk, @RequestBody Order order) throws Exception {

        FormattedResponse response = orderService.updateOrder(pk, order);


        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PutMapping("/{pk}/update_status/{status}")
    public ResponseEntity<?> updateStatus(@PathVariable int pk, @PathVariable String status) throws Exception {

        FormattedResponse response = orderService.updateStatus(pk, status);

        //send email then send response

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

}