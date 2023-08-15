package com.dev.mcc_tools.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearchRequest {
    private String invoiceNumber;
    private String status;
    private String firstName;
    private String lastName;
    private String email;
}
