package com.dev.mcc_tools.validation;

public class AppointmentValidator extends MccValidator {

    public Boolean checkStatus(String status) {
        if (status.equals("pending") || status.equals("delivered") || status.equals("cancelled")) {
            return true;
        }
        setErrors("status", "status must be either pending, delivered or cancelled");
        return false;
    }
}