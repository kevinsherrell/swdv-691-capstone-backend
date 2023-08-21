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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.Format;
import java.text.ParseException;
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
    public ResponseEntity<?> createNewOrder(@Valid @RequestBody Order order, BindingResult result) {
        orderValidator.initializeErrors();


        HttpStatus httpStatus = HttpStatus.CREATED;
        FormattedResponse response;
        HashMap<String, String> errors = orderValidator.getErrors();

        orderValidator.checkOrderFormat(order);

        if (errors.isEmpty()) {
            Order created = orderService.saveOrUpdateOrder(order);
            response = new FormattedResponse(httpStatus.value(), true, created);
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
            response = new ErrorResponse(httpStatus.value(), false, errors);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllOrders() {
        HttpStatus httpStatus = HttpStatus.OK;

        Iterable<Order> found = orderService.findAllOrders();
        FormattedResponse response = new FormattedResponse(httpStatus.value(), true, found);
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/search")
    public ResponseEntity<?> OrderSearch(
            @RequestParam(required = false, name = "invoiceNumber") String invoiceNumber,
            @RequestParam(required = false, name = "status") String status,
//            @RequestParam(required = false, name = "firstName") String firstName,
//            @RequestParam(required = false, name = "lastName") String lastName,
//            @RequestParam(required = false, name = "email") String email,
            @RequestParam(required = false, name = "minDate") String minDate,
            @RequestParam(required = false, name = "maxDate") String maxDate
    ) throws ParseException {
        HttpStatus httpStatus = HttpStatus.OK;
        FormattedResponse response;

        OrderSearchRequest request = new OrderSearchRequest();


        if (invoiceNumber != null) request.setInvoiceNumber(invoiceNumber);
        if (status != null) request.setStatus(status);
//        if (firstName != null) request.setFirstName(firstName);
//        if (lastName != null) request.setLastName(lastName);
//        if (email != null) request.setEmail(email);
        if (minDate != null) request.setMinDate(minDate);
        if (maxDate != null) request.setMaxDate(maxDate);


        Iterable<Order> found = orderSearch.findAllByCriteria(request);
        response = new FormattedResponse(httpStatus.value(), true, found);
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/{pk}")
    public ResponseEntity<?> getOrderByID(@PathVariable int pk) {
        HttpStatus httpStatus = HttpStatus.OK;

        Order found = orderService.findOrderById(pk);
        FormattedResponse response = new FormattedResponse(httpStatus.value(), true, found);
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/profile/{profileID}")
    public ResponseEntity<?> getOrderByProfileID(@PathVariable int profileID) {
        HttpStatus httpStatus = HttpStatus.OK;
        FormattedResponse response;

        Iterable<Order> found = orderService.findByProfileID(profileID);

        response = new FormattedResponse(httpStatus.value(), true, found);
        return new ResponseEntity<>(response, httpStatus);
    }


    @PutMapping("/update/{pk}")
    public ResponseEntity<?> updateOrder(@PathVariable int pk, @RequestBody Order order) {
        orderValidator.initializeErrors();

        HttpStatus httpStatus = HttpStatus.CREATED;
        FormattedResponse response;
        HashMap<String, String> errors = orderValidator.getErrors();

        Order found = orderService.findOrderById(pk);
        System.out.println(found.getOrderID());

        // checks if the order id matches the id given in the path variable
        orderValidator.checkIDMatch(pk, order.getOrderID());
        // checks if the order exists in the database
        orderValidator.nullCheck("Order", found);
        // checks if the order fields are formatted correctly
        orderValidator.checkOrderFormat(order);


        if (errors.isEmpty()) {
            // protects these values from being changed on frontend
            order.setOrderID(found.getOrderID());
            order.setDate_created(found.getDate_created());
            order.setProfileID(found.getProfileID());

            if (!order.getInvoiceNumber().equals(found.getInvoiceNumber()))
                found.setInvoiceNumber(order.getInvoiceNumber());
            if (!order.getStatus().equals(found.getStatus())) found.setStatus(order.getStatus());
            if (!order.getLocation().equals(found.getLocation())) found.setLocation(order.getLocation());

            Order updated = orderService.saveOrUpdateOrder(found);
            response = new FormattedResponse(httpStatus.value(), true, updated);
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
            response = new ErrorResponse(httpStatus.value(), false, errors);
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @PutMapping("/{pk}/update_status/{status}")
    public ResponseEntity<?> updateStatus(@PathVariable int pk, @PathVariable String status) {
        orderValidator.initializeErrors();

        HttpStatus httpStatus = HttpStatus.CREATED;
        FormattedResponse response;

        HashMap<String, String> errors = orderValidator.getErrors();

        Order found = orderService.findOrderById(pk);

        orderValidator.checkStatus(status);
        orderValidator.nullCheck("Order", found);

        if (errors.isEmpty()) {
            found.setStatus(status);
            orderService.saveOrUpdateOrder(found);
            response = new FormattedResponse(httpStatus.value(), true, found);
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
            response = new ErrorResponse(httpStatus.value(), false, errors);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

}