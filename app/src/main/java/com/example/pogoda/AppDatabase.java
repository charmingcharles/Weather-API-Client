package com.example.pogoda;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DatabaseCity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DatabaseCityDAO databaseCityDAO();
}
