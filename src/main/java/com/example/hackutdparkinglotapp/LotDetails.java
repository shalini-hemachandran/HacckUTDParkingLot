package com.example.hackutdparkinglotapp;

/**
 * Created by nsaba on 3/4/2017.
 */

public class LotDetails {

    String lot_id;
    String lot_Name;
    String lot_location;


    public String get_lot_id(){
        return lot_id;
    }
    public String get_lot_name(){
        return lot_Name;
    }
    public String get_lot_location(){
        return lot_location;
    }

    public void setLot_id(String lot_id) {
        this.lot_id = lot_id;
    }

    public void setLot_location(String lot_location) {
        this.lot_location = lot_location;
    }

    public void setLot_Name(String lot_Name) {
        this.lot_Name = lot_Name;
    }
}
