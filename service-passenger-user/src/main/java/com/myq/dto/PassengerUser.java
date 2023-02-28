package com.myq.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalTime;

@Data
public class PassengerUser {
    private long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private String passengerPhone;
    private String passengerName;
    private byte passengerGender;
    private byte state;
    //private String profilephoto;

}
