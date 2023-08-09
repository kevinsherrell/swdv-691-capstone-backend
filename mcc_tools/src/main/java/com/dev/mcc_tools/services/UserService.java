package com.dev.mcc_tools.services;

import com.dev.mcc_tools.domain.User;
import com.dev.mcc_tools.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveOrUpdateUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            e.getCause();
        }
        return user;
    }

    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(int id) {

        User found = userRepository.findById(id);
        return found;
        
    }
}