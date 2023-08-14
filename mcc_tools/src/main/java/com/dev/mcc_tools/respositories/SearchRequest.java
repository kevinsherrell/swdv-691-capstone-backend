package com.dev.mcc_tools.respositories;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

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
        Date formattedDate = new SimpleDateFormat("yyyy-MM-dd Hh:mm").parse(dateString);

        System.out.println(new Timestamp(formattedDate.getTime()));
        return new Timestamp(formattedDate.getTime());
//        Date formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateString);
//        return new Timestamp(formattedDate.getTime());
    }
    public Date dateFromString(String dateString) throws ParseException {
        Date formattedDate = new SimpleDateFormat("yyyy-MM-dd Hh:mm").parse(dateString);
//        System.out.println(formattedDate);
        System.out.println(new Timestamp(formattedDate.getTime()));
        return formattedDate;
    }
}
