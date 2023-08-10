package com.dev.mcc_tools.services;

import com.dev.mcc_tools.domain.PhoneNumber;
import com.dev.mcc_tools.domain.Profile;
import com.dev.mcc_tools.respositories.PhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberService {
    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    public PhoneNumber saveOrUpdatePhoneNumber(PhoneNumber phoneNumber) {
        try {
            return phoneNumberRepository.save(phoneNumber);
        } catch (Exception e) {
            e.getCause();
        }
        return phoneNumber;
    }

    public Iterable<PhoneNumber> saveAllPhoneNumbers(Iterable<PhoneNumber> phoneNumbers) {
        return phoneNumberRepository.saveAll(phoneNumbers);
    }

    public Iterable<PhoneNumber> findAllPhoneNumbers() {
        return phoneNumberRepository.findAll();
    }

    public PhoneNumber findPhoneNumberByID(int phoneID) {

        return phoneNumberRepository.findByPhoneID(phoneID);


    }

    public Iterable<PhoneNumber> findPhoneNumbersByProfileID(int profileID) {
        return phoneNumberRepository.findByProfileID(profileID);
    }
};