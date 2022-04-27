package com.example.pogoda;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

@Entity(tableName = "CITIES")
public class DatabaseCity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    public int ID;

    @ColumnInfo(name = "CITY_NAME")
    public String city_name;

    @ColumnInfo(name = "LATITUDE")
    public double latitude;

    @ColumnInfo(name = "LONGITUDE")
    public double longitude;

    @Ignore
    public DatabaseCity(City city) {
        city_name = city.getName();
        latitude = city.getLatitude();
        longitude = city.getLongitude();
    }

    public DatabaseCity(int ID, String city_name, double latitude, double longitude) {
        this.ID = ID;
        this.city_name = city_name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public DatabaseCity() {
    }

    public int getID() {
        return ID;
    }

    public String getCity_name() {
        return city_name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return city_name + " [" + latitude + " : " + longitude + "]";
    }
}
