package com.example.hackutdparkinglotapp;

/**
 * Created by nsaba on 3/4/2017.
 */

public class Booked_Spots
{

    String user_id;
    String park_id;

    public String get_user_id(){
        return user_id;
    }

    public String get_park_id(){
        return park_id;
    }

    public void setPark_id(String park_id) {
        this.park_id = park_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
