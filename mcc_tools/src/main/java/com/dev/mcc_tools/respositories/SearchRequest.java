package com.dev.mcc_tools.respositories;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
public class SearchRequest {

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
