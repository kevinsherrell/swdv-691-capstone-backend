package com.dev.mcc_tools.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileSearchRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
