package com.dev.mcc_tools.validation;

import com.dev.mcc_tools.domain.Notification;
import com.dev.mcc_tools.domain.PhoneNumber;
import com.dev.mcc_tools.domain.Profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;



public class MccValidator {

    protected HashMap<String, String> errors = new HashMap<>();


    public void setErrors(String field, String message) {
        this.errors.put(field, message);
    }
    public void initializeErrors(){
        this.errors = new HashMap<>();
    }

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public void nullCheck(String objectName, Object object){
        if(object == null){
            setErrors(objectName, objectName + " not found or does not exist");
        }
    }
    public void emptyCheck(String objectName, Iterable<Object> objects){
        if(!objects.iterator().hasNext()){
            setErrors(objectName, objectName + " not found or does not exist");
        }
    }
    public void checkIDMatch(int pk, int requestObjID){
        if(pk != requestObjID){
            setErrors("id mismatch", "Id in path and request body do not match. Change the id in the url path");
        }
    }
}