package com.dev.mcc_tools.validation;

import com.dev.mcc_tools.domain.Profile;

import java.util.HashMap;
import java.util.Map;

public class ProfileValidator extends MccValidator{

    public HashMap<String, String> checkProfile(Profile profile) {
        if (profile == null) {
            setErrors("profile", "profile not found");
        }
        return this.errors;
    }

}