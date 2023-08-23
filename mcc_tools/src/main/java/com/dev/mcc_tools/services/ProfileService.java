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
//                    profile.getUserID(),
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
            httpStatus = HttpStatus.BAD_REQUEST;
            return new ErrorResponse(httpStatus.value(), false, errors);
        }
    }

    public FormattedResponse updateProfile(int profileID, Profile profile) {
        profileValidator.initializeErrors();

        HttpStatus httpStatus = HttpStatus.CREATED;
        HashMap<String, ArrayList<String>> errors = profileValidator.getErrors();

        try {

            Profile found = profileRepository.findById(profileID);
            profileValidator.checkForProfile(found);
            // store the associated user in a variable
            User associatedUser = found.getUser();
            profileValidator.checkIDMatch(found.getProfileID(), profile.getProfileID());

            profileValidator.checkNullOrEmpty(
                    profile.getFirstName(),
                    profile.getLastName(),
//                    profile.getUserID(),
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
            System.out.println(found.getLastName());

            /*
            * problem - profile not showing associated user upon update.
            *
            * Temporary working solution. store the associate user from the found profile.
            * set the user to the created profile after saving and return the result.
            *
            * this solution is working for now. Exploring a more efficient solution.
            * */
            created.setUser(associatedUser);
            return new FormattedResponse(httpStatus.value(), true, created);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
            return new ErrorResponse(httpStatus.value(), false, errors);
        }
    }

    public Iterable<Profile> findAllProfiles() {
        return profileRepository.findAll();
    }

    public FormattedResponse findProfileById(int id) {
        profileValidator.initializeErrors();

        HttpStatus httpStatus = HttpStatus.OK;
        HashMap<String, ArrayList<String>> errors = profileValidator.getErrors();

        try {
            Profile found = profileRepository.findById(id);
            profileValidator.checkForProfile(found);
            return new FormattedResponse(httpStatus.value(), true, found);
        } catch (Exception e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            return new ErrorResponse(httpStatus.value(), false, errors);
        }

    }

    public FormattedResponse findProfileByUserID(int userID) {
        profileValidator.initializeErrors();

        HttpStatus httpStatus = HttpStatus.OK;
        HashMap<String, ArrayList<String>> errors = profileValidator.getErrors();

        try {
            Profile found = profileRepository.findByUserID(userID);
            profileValidator.checkForProfile(found);
            return new FormattedResponse(httpStatus.value(), true, found);
        } catch (Exception e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            return new ErrorResponse(httpStatus.value(), false, errors);
        }
    }
}