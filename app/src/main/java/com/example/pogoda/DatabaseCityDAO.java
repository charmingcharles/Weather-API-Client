package com.example.pogoda;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DatabaseCityDAO {

    @Query("SELECT * FROM CITIES")
    List<DatabaseCity> getAll();

    @Query("DELETE FROM CITIES WHERE city_name = :name")
    void deleteByName(String name);

    @Delete
    void delete(DatabaseCity city);

    @Insert
    void insert(DatabaseCity city);
}
