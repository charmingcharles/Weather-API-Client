package com.example.pogoda;

public class ForecastCity extends City{

    private String[] daysInfo = new String[3];

    private String day1Icon;

    private String day2Icon;

    private String day3Icon;

    public ForecastCity(double latitude, double longitude, String name) {
        super(latitude, longitude, name);
    }

    public String getDay1Info() {
        return daysInfo[0];
    }

    public void setDay1Info(String day1Info) {
        this.daysInfo[0] = day1Info;
    }

    public String getDay1Icon() {
        return day1Icon;
    }

    public void setDay1Icon(String day1Icon) {
        this.day1Icon = day1Icon;
    }

    public String getDay2Info() {
        return daysInfo[1];
    }

    public void setDay2Info(String day2Info) {
        this.daysInfo[1] = day2Info;
    }

    public String getDay2Icon() {
        return day2Icon;
    }

    public void setDay2Icon(String day2Icon) {
        this.day2Icon = day2Icon;
    }

    public String getDay3Info() {
        return daysInfo[2];
    }

    public void setDay3Info(String day3Info) {
        this.daysInfo[2] = day3Info;
    }

    public String[] getDaysInfo() {
        return daysInfo;
    }

    public String getDay3Icon() {
        return day3Icon;
    }

    public void setDay3Icon(String day3Icon) {
        this.day3Icon = day3Icon;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
