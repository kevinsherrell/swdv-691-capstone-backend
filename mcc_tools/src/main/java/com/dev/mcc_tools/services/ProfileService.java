package com.dev.mcc_tools.services;

import com.dev.mcc_tools.domain.Profile;
import com.dev.mcc_tools.domain.User;
import com.dev.mcc_tools.respositories.ProfileRepository;
import com.dev.mcc_tools.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public Profile saveOrUpdateProfile(Profile profile) {
        try {
            return profileRepository.save(profile);
        } catch (Exception e) {
            e.getCause();
        }
        return profile;
    }

    public Iterable<Profile> findAllProfiles() {
        return profileRepository.findAll();
    }

    public Profile findProfileById(int id) {

        Profile found = profileRepository.findById(id);
        return found;

    }

    public Profile findProfileByUserID(int userID){
        Profile found = profileRepository.findByUserID(userID);
        return found;
    }
}