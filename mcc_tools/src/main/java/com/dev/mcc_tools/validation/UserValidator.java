package com.dev.mcc_tools.validation;

import com.dev.mcc_tools.domain.User;

import java.util.HashMap;
import java.util.Map;

public class UserValidator extends MccValidator {


    public void checkPasswordUpdate(String oldPassword, Map<String, String> updateObj) {

        if (!oldPassword.equals(updateObj.get("oldVerify"))) {
            setErrors("password", "old password is incorrect");
        }
        if (!updateObj.get("newPassword").equals(updateObj.get("passwordVerify"))) {
            System.out.println(updateObj.get("newPassword").equals(updateObj.get("passwordVerify")));
            setErrors("password", "new password and verification must match");
        }
    }
    public void checkUserForUpdate(User foundUser){

        if(foundUser == null){
            setErrors("user", "this user does not exist");
        }

    }
}