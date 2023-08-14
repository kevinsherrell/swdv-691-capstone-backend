package com.dev.mcc_tools.validation;

import com.dev.mcc_tools.domain.Profile;

public class ProfileValidator extends MccValidator{

    public void checkProfile(Profile profile) {
        if (profile == null) {
            setErrors("profile", "profile not found");
        }
    }

}