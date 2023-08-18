package com.dev.mcc_tools.services;

import com.dev.mcc_tools.domain.User;
import com.dev.mcc_tools.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveOrUpdateUser(User user) {
        System.out.println("updating user");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User updated = userRepository.save(user);
        return updated;
    }

    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(int id) {

        User found = userRepository.findById(id);
        return found;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}