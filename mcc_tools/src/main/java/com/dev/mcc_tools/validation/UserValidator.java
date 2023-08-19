package com.dev.mcc_tools.validation;

import com.dev.mcc_tools.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


public class UserValidator extends MccValidator {
//    @Autowired
    private  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


//    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public void checkPasswordUpdate(String oldPassword, Map<String, String> updateObj) {


        if (!passwordEncoder.matches(updateObj.get("oldVerify"), oldPassword)) {
            setErrors("password", "old password is incorrect");
        }
        if (!updateObj.get("newPassword").equals(updateObj.get("passwordVerify"))) {
            setErrors("password", "new password and verification must match");
        }
    }

    public void checkUserForUpdate(User foundUser) {

        if (foundUser == null) {
            setErrors("user", "this user does not exist");
        }

    }
}