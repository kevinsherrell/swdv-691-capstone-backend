package com.dev.mcc_tools.services;

import com.dev.mcc_tools.controllers.ErrorResponse;
import com.dev.mcc_tools.controllers.FormattedResponse;
import com.dev.mcc_tools.domain.Order;
import com.dev.mcc_tools.domain.Profile;
import com.dev.mcc_tools.domain.User;
import com.dev.mcc_tools.email.EmailSenderService;
import com.dev.mcc_tools.respositories.OrderRepository;
import com.dev.mcc_tools.respositories.ProfileRepository;
import com.dev.mcc_tools.respositories.UserRepository;
import com.dev.mcc_tools.validation.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    private EmailSenderService senderService;
    private final OrderValidator orderValidator = new OrderValidator();

    public FormattedResponse saveOrder(Order order) {
        orderValidator.initializeErrors();

        HttpStatus httpStatus = HttpStatus.CREATED;
        HashMap<String, ArrayList<String>> errors = orderValidator.getErrors();

        try {

            orderValidator.initiateOrderChecks(order);

            Order created = orderRepository.save(order);

            return new FormattedResponse(httpStatus.value(), true, created);

        } catch (Exception e) {

            httpStatus = HttpStatus.BAD_REQUEST;

            e.printStackTrace();

            return new ErrorResponse(httpStatus.value(), false, errors);
        }

    }

    public FormattedResponse updateOrder(int orderID, Order order) {
        orderValidator.initializeErrors();
        HttpStatus httpStatus = HttpStatus.CREATED;
        HashMap<String, ArrayList<String>> errors = orderValidator.getErrors();

        try {
            orderValidator.checkIDMatch(orderID, order.getOrderID());

            Order found = orderRepository.findById(order.getOrderID());

            orderValidator.nullCheck("Order", found);

            orderValidator.initiateOrderChecks(order);

            order.setDateCreated(found.getDateCreated());
            Order updated = orderRepository.save(order);

            Profile sendTo = profileRepository.findById(updated.getProfileID());

            if (!order.getStatus().equals(found.getStatus()) && order.getStatus().equals("arrived")) {
                senderService.sendEmail(sendTo.getUser().getEmail(), "Your Order Has Arrived", "HI " + sendTo.getFirstName() + ", your order has arrived! Please log in to the customer portal and schedule your pickup");
            }
            return new FormattedResponse(httpStatus.value(), true, updated);

        } catch (Exception e) {

            httpStatus = HttpStatus.BAD_REQUEST;

            e.printStackTrace();

            return new ErrorResponse(httpStatus.value(), false, errors);
        }
    }

    public FormattedResponse updateStatus(int orderID, String status) {
        orderValidator.initializeErrors();
        HttpStatus httpStatus = HttpStatus.CREATED;
        HashMap<String, ArrayList<String>> errors = orderValidator.getErrors();

        try {
            Order found = orderRepository.findById(orderID);

            orderValidator.nullCheck("Order", found);

            orderValidator.checkStatus(status);

            found.setStatus(status);

            Order updated = orderRepository.save(found);

            // find profile and send email
            Profile sendTo = profileRepository.findById(updated.getProfileID());

            if (status.equals("arrived")) {
                senderService.sendEmail(sendTo.getUser().getEmail(), "Your Order Has Arrived", "HI " + sendTo.getFirstName() + ", your order has arrived! Please log in to the customer portal and schedule your pickup");
            }
            return new FormattedResponse(httpStatus.value(), true, updated);

        } catch (Exception e) {

            httpStatus = HttpStatus.BAD_REQUEST;

            e.printStackTrace();

            return new ErrorResponse(httpStatus.value(), false, errors);
        }
    }

    public FormattedResponse findAllOrders() {
        HttpStatus httpStatus = HttpStatus.OK;

        Iterable<Order> orders = orderRepository.findAll();
        return new FormattedResponse(httpStatus.value(), true, orders);

    }

    public FormattedResponse findOrderById(int orderID) {
        orderValidator.initializeErrors();
        HttpStatus httpStatus = HttpStatus.OK;
        HashMap<String, ArrayList<String>> errors = orderValidator.getErrors();

        try {
            Order found = orderRepository.findById(orderID);

            orderValidator.nullCheck("Order", found);

            return new FormattedResponse(httpStatus.value(), true, found);

        } catch (Exception e) {

            httpStatus = HttpStatus.BAD_REQUEST;

            return new ErrorResponse(httpStatus.value(), false, errors);

        }

    }

    public FormattedResponse findAllByProfileID(int profileID) {
        orderValidator.initializeErrors();
        HttpStatus httpStatus = HttpStatus.OK;
        HashMap<String, ArrayList<String>> errors = orderValidator.getErrors();

        try {
            Iterable<Order> found = orderRepository.findOrderByProfileID(profileID);
            orderValidator.emptyCheck("Orders", found);

            return new FormattedResponse(httpStatus.value(), true, found);

        } catch (Exception e) {

            httpStatus = HttpStatus.BAD_REQUEST;

            return new ErrorResponse(httpStatus.value(), false, errors);

        }
    }
}