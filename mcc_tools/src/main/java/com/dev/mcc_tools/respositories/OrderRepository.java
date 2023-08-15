package com.dev.mcc_tools.respositories;

import com.dev.mcc_tools.domain.Order;
import com.dev.mcc_tools.domain.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    Iterable<Order> findAll();
    Order findById(int id);
    Iterable<Order> findOrderByProfileID(int userID);
}