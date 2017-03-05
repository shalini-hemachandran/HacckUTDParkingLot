package com.example.hackutdparkinglotapp;

/**
 * Created by nsaba on 3/4/2017.
 */

public class Parking_Spot_Details {

    String lot_id;
    String park_id;
    String park_space_name;
    String park_coordinates;

    public String get_lot_id(){
        return lot_id;
    }
    public String park_space_name(){
        return park_space_name;
    }
    public String park_cost(){
        return park_coordinates;
    }


    public void setLot_id(String lot_id) {
        this.lot_id = lot_id;
    }

    public void setPark_id(String park_id) {
        this.park_id = park_id;
    }

    public void setPark_coordinates(String park_coordinates) {
        this.park_coordinates = park_coordinates;
    }

    public void setPark_space_name(String park_space_name) {
        this.park_space_name = park_space_name;
    }
}
