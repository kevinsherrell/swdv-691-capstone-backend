package com.dev.mcc_tools.validation;

import com.dev.mcc_tools.domain.Profile;

import java.util.regex.Pattern;

public class ProfileValidator extends MccValidator {

    public void checkForProfile(Profile profile) {
        if (profile == null) {
            setErrors("profile", "profile not found");
        }
    }

    public void checkNullOrEmpty(
            String firstName,
            String lastName,
            int userID,
            String address1,
            String city,
            String state,
            String zipCode,
            String phoneNumber,
            String phoneType
    ) throws Exception {
        if (firstName == null || firstName.isEmpty()) {
            setErrors("firstName", "first name cannot be empty");
        }

        if (lastName == null || lastName.isEmpty()) {
            setErrors("lastName", "last name cannot be empty");
        }

        if (userID < 1) {
            setErrors("userID", "userID must not be empty");
        }


        if (address1 == null || address1.isEmpty()) {
            setErrors("address1", "address1 cannot be empty");
        }


        if (city == null || city.isEmpty()) {
            setErrors("city", "city cannot be empty");
        }

        if (state == null || state.isEmpty()) {
            setErrors("state", "state must not be empty");
        }

        if (zipCode == null || zipCode.isEmpty()) {
            setErrors("zipCode", "zip code must not be empty");
        }
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            setErrors("phoneNumber", "phone number  must not be empty");
        }
        if (phoneType == null || phoneType.isEmpty()) {
            setErrors("phoneType", "phone type must not be empty");
        }
        if(!errors.isEmpty()){
            throw new Exception();
        }
    }

    public void checkFormat(String state, String zipCode, String phoneNumber, String phoneType) throws Exception {
        String zipRegex = "\\d\\d\\d\\d\\d";
        String phoneRegex = "\\d\\d\\d\\d\\d\\d\\d";
        if (state.split("").length != 2) {
            setErrors("state", "state must be two character abbreviation");
        }
        if (!Pattern.matches(zipRegex, zipCode)) {
            setErrors("zipCode", "zip code must be 5 numbers");
        }
        if (!Pattern.matches(phoneRegex, phoneNumber)) {
            setErrors("phoneNumber", "phone number must be 7 digits. no special characters i.e )(-.");
        }
        if (!phoneType.equals("MOBILE") && !phoneType.equals("LANDLINE")) {
            setErrors("phoneNumber", "phone type must be either MOBILE or LANDLINE");
        }

        if(!errors.isEmpty()){
            throw new Exception();
        }
    }
}