package com.dev.mcc_tools.validation;

import java.util.Date;
import java.util.regex.Pattern;

public class AppointmentValidator extends MccValidator {

    public void checkStatus(String status) throws Exception {
        if (!status.equals("pending") && !status.equals("delivered") && !status.equals("cancelled")) {
            setErrors("status", "status must be either pending, delivered or cancelled");
        }
        if(!errors.isEmpty()){
            throw new Exception();
        }
    }
//    public void checkDateFormat(Date date) throws Exception {
//        String dateRegex = "\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d";
//        if(!Pattern.matches(dateRegex, dateString)){
//            setErrors("date", "incorrect date format. accepted format is yyyy-MM-dd HH:mm");
//        }
//        throw new Exception();
//    }
}