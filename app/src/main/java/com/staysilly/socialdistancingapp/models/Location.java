package com.staysilly.socialdistancingapp.models;

public class Location {

    /*/////////////////////////////////////////////////
    //MEMBERS
    /*/////////////////////////////////////////////////
    private final String TAG = this.getClass().getSimpleName();
    private String userId;
    private double latitude;
    private double longitude;
    private boolean isOnline;


    /*/////////////////////////////////////////////////
    //PROPERTY
    /*/////////////////////////////////////////////////
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public boolean isOnline() {
        return isOnline;
    }
    public void setOnline(boolean online) {
        isOnline = online;
    }


    /*/////////////////////////////////////////////////
    //CONSTRUCTOR
    /*/////////////////////////////////////////////////
    public Location(String id, double latitude, double longitude, boolean isOnline){
        this.userId = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isOnline = isOnline;
    }
    public Location(){}

}
