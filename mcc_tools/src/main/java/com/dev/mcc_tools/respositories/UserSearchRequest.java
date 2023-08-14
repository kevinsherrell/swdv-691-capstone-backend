package com.dev.mcc_tools.respositories;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class UserSearchRequest {

    private Integer roleID;
    private String email;

}
