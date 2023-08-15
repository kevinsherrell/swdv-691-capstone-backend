package com.dev.mcc_tools.validation;

import com.dev.mcc_tools.domain.Address;

public class AddressValidator extends MccValidator {

    public Boolean addressCheck(Address fromDB, Address requestBody) {

        addressFound(fromDB);
        checkAddressFormat(requestBody);

        if (errors.isEmpty()) {
            return true;
        }
        return false;
    }

    public void checkAddressFormat(Address requestBody) {
        try {
            if (requestBody.getZipCode().length() != 5) {
                setErrors("zipCode", "zipcode must consist of 5 numbers");
            }
            Integer.parseInt(requestBody.getZipCode());
        } catch (NumberFormatException e) {
            setErrors("zipCode", "zip code must contain all numbers");
        }

    }

    public void addressFound(Address found) {
        if (found == null) {
            setErrors("address", "address not found");
        }
    }
}
