package com.dev.mcc_tools.validation;

import com.dev.mcc_tools.domain.PhoneNumber;
import com.dev.mcc_tools.domain.Profile;

import java.util.HashMap;
import java.util.Map;

public class MccValidator {

    private HashMap<String, String> errors = new HashMap<>();

    public HashMap<String, String> checkPasswordUpdate(String oldPassword, Map<String, String> updateObj) {

        if (!oldPassword.equals(updateObj.get("oldVerify"))) {
            setErrors("password", "old password is incorrect");
        } else if (!updateObj.get("newPassword").equals(updateObj.get("passwordVerify"))) {
            setErrors("password", "new password and verification must match");
        }

        return this.errors;
    }

    public HashMap<String, String> checkProfile(Profile profile) {
        if (profile == null) {
            setErrors("profile", "profile not found");
        }
        return this.errors;
    }
    public HashMap<String, String> checkPhoneNumber(PhoneNumber phoneNumber){
        if(phoneNumber == null){
            setErrors("phoneNumber", "profile not found");
        }
        return this.errors;
    }
    public void setErrors(String field, String message) {
        this.errors.put(field, message);
    }

    public HashMap<String, String> getErrors() {
        return errors;
    }
}