package com.myq.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class PassengerUser {
    private long id;
    private LocalTime gmtCreate;
    private LocalTime gmtModified;
    private String passengerPhone;
    private String passengerName;
    private byte passengerGender;
    private byte state;
    //private String profilephoto;

}
