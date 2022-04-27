package com.example.pogoda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteClosable;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {


    public DatabaseHandler(@Nullable Context context) {
        super(context, "cities.db", null, 1, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableString = "CREATE TABLE CITIES(" + "ID INTEGER PRIMARY KEY AUTOINCREMENT," + "CITY_NAME TEXT," + "LATITUDE REAL," + "LONGITUDE REAL" +")";
       db.execSQL(createTableString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addCity(City city){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("CITY_NAME", city.getName());
        cv.put("LATITUDE", city.getLatitude());
        cv.put("LONGITUDE", city.getLongitude());
        long insertResult = db.insert("CITIES",null, cv);
        return insertResult >= 0;
    }

    public boolean update(City city, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("CITY_NAME", city.getName());
        cv.put("LATITUDE", city.getLatitude());
        cv.put("LONGITUDE", city.getLongitude());
        long insertResult = db.update("CITIES", cv, "ID = ?", new String[]{Integer.toString(id + 1)});
        return insertResult >= 0;
    }

    public boolean deleteCity(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        //db.rawQuery("DELETE FROM CITIES WHERE CITY_NAMEE = ? LIMIT ?", new String[]{name, "1"});
        //db.execSQL("DELETE FROM CITIES WHERE CITY_NAME = " + name + " LIMIT 1");
        return db.delete("CITIES",  "CITY_NAME = ?", new String[]{name}) > 0;
    }

    public List<City> getAllCities(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<City> cities = new ArrayList<>();
        String queryString = "SELECT * FROM CITIES";
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                double latitude = cursor.getDouble(2);
                double longitude = cursor.getDouble(3);
                City city = new City(latitude, longitude, name);
                cities.add(city);
                System.out.println("???");
            }while (cursor.moveToNext());
        }
        else{
            //empty
        }
        cursor.close();
        db.close();
        return cities;
    }

}
