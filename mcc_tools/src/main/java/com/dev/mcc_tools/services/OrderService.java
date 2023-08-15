package com.dev.mcc_tools.services;

import com.dev.mcc_tools.domain.Order;
import com.dev.mcc_tools.domain.Profile;
import com.dev.mcc_tools.domain.User;
import com.dev.mcc_tools.respositories.OrderRepository;
import com.dev.mcc_tools.respositories.ProfileRepository;
import com.dev.mcc_tools.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order saveOrUpdateOrder(Order order) {
        try {
            return orderRepository.save(order);
        } catch (Exception e) {
            e.getCause();
        }
        return null;
    }

    public Iterable<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Order findOrderById(int orderID) {

        Order found = orderRepository.findById(orderID);
        return found;

    }

    public Order findByProfileID(int profileID){
        Order found = orderRepository.findOrderByProfileID(profileID);
        return found;
    }
}