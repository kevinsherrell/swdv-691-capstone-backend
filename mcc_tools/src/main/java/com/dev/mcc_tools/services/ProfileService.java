package com.dev.mcc_tools.services;

import com.dev.mcc_tools.controllers.ErrorResponse;
import com.dev.mcc_tools.controllers.FormattedResponse;
import com.dev.mcc_tools.domain.Profile;
import com.dev.mcc_tools.domain.User;
import com.dev.mcc_tools.respositories.ProfileRepository;
import com.dev.mcc_tools.respositories.UserRepository;
import com.dev.mcc_tools.validation.ProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    private final ProfileValidator profileValidator = new ProfileValidator();

    public FormattedResponse saveProfile(Profile profile) {
        profileValidator.initializeErrors();

        HttpStatus httpStatus = HttpStatus.CREATED;
        HashMap<String, ArrayList<String>> errors = profileValidator.getErrors();

        try {
            profileValidator.checkNullOrEmpty(
                    profile.getFirstName(),
                    profile.getLastName(),
                    profile.getUserID(),
                    profile.getAddress1(),
                    profile.getCity(),
                    profile.getState(),
                    profile.getZipCode(),
                    profile.getPhoneNumber(),
                    profile.getPhoneType()
            );
            profileValidator.checkFormat(
                    profile.getState(),
                    profile.getZipCode(),
                    profile.getPhoneNumber(),
                    profile.getPhoneType()
            );
            Profile created = profileRepository.save(profile);
            return new FormattedResponse(httpStatus.value(), true, created);
        } catch (Exception e) {
            return new ErrorResponse(httpStatus.value(), false, errors);
        }
    }

//    public Profile saveProfile(Profile profile) {
//        try {
//            return profileRepository.save(profile);
//        } catch (Exception e) {
//            e.getCause();
//        }
//        return profile;
//    }

    public Iterable<Profile> findAllProfiles() {
        return profileRepository.findAll();
    }

    public Profile findProfileById(int id) {

        Profile found = profileRepository.findById(id);
        return found;

    }

    public Profile findProfileByUserID(int userID) {
        Profile found = profileRepository.findByUserID(userID);
        return found;
    }
}