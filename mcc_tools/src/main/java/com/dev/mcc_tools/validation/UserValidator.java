package com.dev.mcc_tools.validation;

import com.dev.mcc_tools.domain.User;
import com.dev.mcc_tools.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;


public class UserValidator extends MccValidator {
    //    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public void checkEmail(String email) {
        System.out.println("checking email");
        if (email.isEmpty()) {
            setErrors("email", "email must not be empty");
            throw new IllegalArgumentException();
        }
    }

    public void checkPassword(String password) {
        System.out.println("checking password");
        if (password.isEmpty()) {
            setErrors("password", "password must not be empty");
            throw new IllegalArgumentException();
        }
    }

    public void checkPasswordUpdate(String oldPassword, Map<String, String> updateObj) throws Exception {
        System.out.println("checking passwords for update");
        System.out.println(passwordEncoder.matches(updateObj.get("oldVerify"), oldPassword));
        if (!passwordEncoder.matches(updateObj.get("oldVerify"), oldPassword)) {
            System.out.println("passsword not verified");
            setErrors("password", "old password is incorrect");
            throw new Exception();
        }
        if (!updateObj.get("newPassword").equals(updateObj.get("passwordVerify"))) {
            setErrors("password", "new password and verification must match");
            throw new Exception();
        }

    }

    public void checkUserForUpdate(User foundUser) {
        System.out.println("checking user for update");

        if (foundUser == null) {
            setErrors("user", "this user does not exist");
            throw new NotFoundException("user not found or does not exist");
        }

    }
}