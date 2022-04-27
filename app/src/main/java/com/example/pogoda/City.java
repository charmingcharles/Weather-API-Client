package com.example.pogoda;

public class City {

    private double latitude;

    private double longitude;

    private String name;

    public City(double latitude, double longitude, String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City(DatabaseCity city){
        this.name = city.city_name;
        this.latitude = city.latitude;
        this.longitude = city.longitude;
    }

    @Override
    public String toString() {
        return "City{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", name='" + name +
                '}';
    }
}

