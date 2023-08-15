package com.dev.mcc_tools.search;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class NotificationSearchRequest {

    private String status;
    private Integer profileID;
    private String minDate;
    private String maxDate;
    private String minTime;
    private String maxTime;

    public Timestamp toTimestamp(String dateString) throws ParseException {
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime ldt = LocalDateTime.parse(dateString, dtFormat);

        System.out.println("localDateTime : " + ldt);


        return Timestamp.from(ldt.toInstant(ZoneOffset.UTC));
    }

}
