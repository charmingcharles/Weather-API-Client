package com.example.pogoda;

public class WeatherCity extends City{

    private String mainInfo;
    private String mainIcon;
    private String additionalInfo;
    private String additionalIcon;

    public WeatherCity(double latitude, double longitude, String name) {
        super(latitude, longitude, name);
    }

    public String getMainInfo() {
        return mainInfo;
    }

    public void setMainInfo(String mainInfo) {
        this.mainInfo = mainInfo;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getMainIcon() {
        return mainIcon;
    }

    public void setMainIcon(String mainIcon) {
        this.mainIcon = mainIcon;
    }

    public String getAdditionalIcon() {
        return additionalIcon;
    }

    public void setAdditionalIcon(String additionalIcon) {
        this.additionalIcon = additionalIcon;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
