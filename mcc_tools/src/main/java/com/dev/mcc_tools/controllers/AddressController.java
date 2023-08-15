package com.dev.mcc_tools.controllers;

import com.dev.mcc_tools.domain.Address;
import com.dev.mcc_tools.domain.PhoneNumber;
import com.dev.mcc_tools.services.AddressService;
import com.dev.mcc_tools.services.PhoneNumberService;
import com.dev.mcc_tools.validation.AddressValidator;
import com.dev.mcc_tools.validation.MccValidator;
import com.dev.mcc_tools.validation.PhoneNumberValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/address")
@CrossOrigin
public class AddressController {
    @Autowired
    private AddressService addressService;
    private final AddressValidator addressValidator = new AddressValidator();

    @PostMapping("")
    public ResponseEntity<?> createAddress(@Valid @RequestBody Address address, BindingResult result) {
        addressValidator.initializeErrors();

        HttpStatus httpStatus = HttpStatus.CREATED;
        HashMap<String, String> errors = addressValidator.getErrors();
        FormattedResponse response;
        addressValidator.checkAddressFormat(address);
        if (errors.isEmpty()) {
            Address created = addressService.saveOrUpdateAddress(address);
            response = new FormattedResponse(httpStatus.value(), true, created);
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
            response = new ErrorResponse(httpStatus.value(), false, errors);
        }


        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllAddresses() {
        HttpStatus httpStatus = HttpStatus.OK;

        Iterable<Address> found = addressService.findAllAddresses();
        FormattedResponse response = new FormattedResponse(HttpStatus.OK.value(), true, found);

        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/{addressID}")
    public ResponseEntity<?> getAddressByID(@PathVariable int addressID) {
        HttpStatus httpStatus = HttpStatus.OK;

        Address found = addressService.findByAddressID(addressID);

        FormattedResponse response = new FormattedResponse(httpStatus.value(), true, found);
        return new ResponseEntity<>(response, httpStatus);
    }

    @PutMapping("/update/{pk}")
    public ResponseEntity<?> updateAddress(@PathVariable int pk, @RequestBody Address address) {
        addressValidator.initializeErrors();

        HttpStatus httpStatus = HttpStatus.CREATED;
        HashMap<String, String> errors = addressValidator.getErrors();

        FormattedResponse response;


        Address found = addressService.findByAddressID(pk);

        addressValidator.addressCheck(found, address);

        if (errors.isEmpty()) {
            Address updated = addressService.saveOrUpdateAddress(address);
            response = new FormattedResponse(httpStatus.value(), true, updated);
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
            response = new ErrorResponse(httpStatus.value(), false, errors);
        }

        return new ResponseEntity<>(response, httpStatus);
    }


}