package com.example.administrator.pragatimannya;

/**
 * Created by Administrator on 7/15/2017.
 */

public class AdminAddTaskData {
    public String description;
    public String geolat;
    public String geolong;
    public String address;
    public String type;
    public String status;
    public String adminuid;

    public AdminAddTaskData() {
        //Emptyconstructor
    }

    public AdminAddTaskData(String description, String geolat, String geolong, String address, String type, String status,String adminuid) {
        this.description = description;
        this.geolat = geolat;
        this.geolong = geolong;
        this.address = address;
        this.type = type;
        this.status = status;
        this.adminuid=adminuid;
    }

    public String getDescription() {
        return this.description;
    }

    public String getGeolat() {
        return this.geolat;
    }

    public String getGeolong() {
        return this.geolong;
    }

    public String getAddress() {
        return this.address;
    }

    public String getType() {
        return this.type;
    }

    public String getStatus() {
        return this.status;
    }

    public String getAdminuid(){
        return this.adminuid;
    }
}
