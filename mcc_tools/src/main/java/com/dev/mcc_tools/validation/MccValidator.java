package com.dev.mcc_tools.validation;

import java.util.HashMap;
import java.util.Map;

public class MccValidator {

    private HashMap<String, String> errors = new HashMap<>();

    public HashMap<String, String> checkPasswordUpdate( String oldPassword, Map<String, String> updateObj){

            if(!oldPassword.equals(updateObj.get("oldVerify"))){
                setErrors("password", "old password is incorrect");
            }else if(!updateObj.get("newPassword").equals(updateObj.get("passwordVerify"))){
                setErrors("password", "new password and verification must match");
            }

            if (this.errors.isEmpty()){
                return null;
            }

            return this.errors;

    }

    public void setErrors(String field, String message) {
        this.errors.put(field, message);
    }
}