package com.example.pogoda;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.time.LocalDateTime;

public class WeatherWrapper {

    private static AstroCalculator astroCalculator;
    private static boolean isInit = false;

    private static double lon;
    private static double lat;

    public static void init(){
        if(!isInit){
            astroCalculator = new AstroCalculator(new AstroDateTime(), new AstroCalculator.Location(0, 0));
            lat = 0;
            lon = 0;
            isInit = true;
        }
    }

    public static void setTime(){
        LocalDateTime local = LocalDateTime.now();
        astroCalculator.setDateTime(new AstroDateTime(local.getYear(), local.getMonth().getValue(), local.getDayOfMonth(), local.getHour(), local.getMinute(), local.getSecond(), 1, true));
    }

    public static AstroDateTime getTime(){
        return astroCalculator.getDateTime();
    }

    public static void setLongitude(double longitude){
        astroCalculator.setLocation(new AstroCalculator.Location(astroCalculator.getLocation().getLatitude(), longitude));
        lon = longitude;
    }

    public static void setLatitude(double latitude){
        astroCalculator.setLocation(new AstroCalculator.Location(latitude, astroCalculator.getLocation().getLongitude()));
        lat = latitude;
    }

    public static AstroDateTime getSunrise(){
        return astroCalculator.getSunInfo().getSunrise();
    }

    public static double getAzimuthSet(){
        return astroCalculator.getSunInfo().getAzimuthSet();
    }

    public static double getAzimuthRise(){
        return astroCalculator.getSunInfo().getAzimuthRise();
    }

    public static AstroDateTime getTwilightMorning(){
        return astroCalculator.getSunInfo().getTwilightMorning();
    }

    public static AstroDateTime getTwilightEvening(){
        return astroCalculator.getSunInfo().getTwilightEvening();
    }

    public static AstroDateTime getSunset(){
        return astroCalculator.getSunInfo().getSunset();
    }

    public static double getAge(){
        return astroCalculator.getMoonInfo().getAge();
    }

    public static double getIllumination(){
        return astroCalculator.getMoonInfo().getIllumination();
    }

    public static AstroDateTime getNextNewMoon(){
        return astroCalculator.getMoonInfo().getNextNewMoon();
    }

    public static AstroDateTime getNextFullMoon(){
        return astroCalculator.getMoonInfo().getNextFullMoon();
    }

    public static AstroDateTime getMoonrise(){
        return astroCalculator.getMoonInfo().getMoonrise();
    }

    public static AstroDateTime getMoonset(){
        return astroCalculator.getMoonInfo().getMoonset();
    }

    public static AstroCalculator.Location getLocation(){
        return astroCalculator.getLocation();
    }

    public static double getLon() {
        return lon;
    }

    public static double getLat() {
        return lat;
    }
}
