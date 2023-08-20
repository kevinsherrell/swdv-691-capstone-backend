package com.dev.mcc_tools.search;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProfileSearchRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
