package com.dev.mcc_tools.validation;

import com.dev.mcc_tools.domain.Notification;
//import com.dev.mcc_tools.domain.PhoneNumber;
import com.dev.mcc_tools.domain.Profile;

import java.util.*;


public class MccValidator {

//    protected HashMap<String, String> errors = new HashMap<>();

    protected HashMap<String, ArrayList<String>> errors = new HashMap<>();


    public void setErrors(String field, String message) {
        if(errors.containsKey(field)){
            errors.get(field).add(message);
        }else{
            errors.put(field, new ArrayList<>(Arrays.asList(message)));
        }
    }
//    public void setErrors(String field, String message) {
//        this.errors.put(field, message);
//    }
    public void initializeErrors(){
        this.errors = new HashMap<>();
    }

//    public HashMap<String, String> getErrors() {
//        return errors;
//    }
    public HashMap<String, ArrayList<String>> getErrors() {
        return errors;
    }

    public void nullCheck(String objectName, Object object){
        if(object == null){
            setErrors(objectName, objectName + " not found or does not exist");
        }
    }
    public void emptyCheck(String objectName, Iterable<?> objects){
        if(!objects.iterator().hasNext()){
            setErrors(objectName, objectName + " not found or does not exist");
        }
    }
    public void checkIDMatch(int pk, int requestObjID){
        System.out.println("checking if IDs match");
        if(pk != requestObjID){
            setErrors("id mismatch", "Id in path and request body do not match. Change the id in the url path");
            throw new InputMismatchException();
        }
    }
}