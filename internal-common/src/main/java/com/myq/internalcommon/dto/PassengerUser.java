package com.myq.internalcommon.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PassengerUser {
    private long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private String passengerPhone;
    private String passengerName;
    private byte passengerGender;
    private byte state;
    private String profilePhoto;

}
