package com.example.pogoda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    public static boolean isSwitchChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        TextView textView = (TextView) findViewById(R.id.clockText);
        textView.setText(WeatherWrapper.getTime().toString());

        Button button = (Button) findViewById(R.id.listViewActivityButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, ListViewActivity.class));
            }
        });

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "test-database").build();

        EditText latitudeInput = (EditText) findViewById(R.id.latitudeTextinput);
        EditText longitudeInput = (EditText) findViewById(R.id.longitudeTextInput);
        EditText city1_Input = (EditText) findViewById(R.id.cityTextInput1);
        TextView city_1_TextView = (TextView) findViewById(R.id.city_1_textView);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    List<DatabaseCity> cities = db.databaseCityDAO().getAll();
                    DatabaseCity city1 = cities.get(0);
                    city_1_TextView.setText(city1.getCity_name());
                    WeatherController.setFavouriteCity(new City(city1), 0);
                    if(WeatherController.getSelectedItem() != -1){
                        city_1_TextView.setText(cities.get(WeatherController.getSelectedItem()).getCity_name());
                        }
                    else
                        city_1_TextView.setText("No selection");
                }
                catch (IndexOutOfBoundsException e){
                    city_1_TextView.setText("No selection!");
                }
            }
        }).start();
        Button submitSettingsButton = (Button) findViewById(R.id.submitSettingsButton);
        submitSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitudeString = latitudeInput.getText().toString();
                if(Validator.validateLatitude(latitudeString)){
                    double latitudeValue = Double.parseDouble(latitudeString);
                    WeatherWrapper.setLatitude(latitudeValue);
                    //latitudeView.setText("Latitude: " + WeatherWrapper.getLocation().getLatitude());
                }
                String longitudeString = longitudeInput.getText().toString();
                boolean coord_check = false;
                if(Validator.validateLongitude(longitudeString)){
                    double longitudeValue = Double.parseDouble(longitudeString);
                    WeatherWrapper.setLongitude(longitudeValue);
                    coord_check = true;
                }
                if(coord_check){
                    WeatherDownloader weatherDownloader = new WeatherDownloader();
                    boolean result = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("unitsPreference", true);
                    WeatherDownloader.setCelsius(result);
                    weatherDownloader.setCoordsResponse(WeatherWrapper.getLat(), WeatherWrapper.getLon());
                    weatherDownloader.setDatabase(db);
                    weatherDownloader.execute();
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                    }
                    if(weatherDownloader.getResponse() < 400){
                        //city_1_TextView.setText(WeatherWrapper.getLocation().getLatitude() + " " + WeatherWrapper.getLocation().getLongitude());
                    }
                }else{
                    String city1_String = city1_Input.getText().toString();
                    if(city1_String.length() > 0){
                        WeatherDownloader weatherDownloader = new WeatherDownloader();
                        boolean result = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("unitsPreference", true);
                        WeatherDownloader.setCelsius(result);
                        weatherDownloader.setNameResponse(city1_String);
                        weatherDownloader.setDatabase(db);
                        weatherDownloader.execute();
                        try{
                            Thread.sleep(1000);
                        }catch (InterruptedException e){
                        }
                        if(weatherDownloader.getResponse() < 400){
                            //city_1_TextView.setText(city1_String);
                        }
                    }
                }
            }
        });

        Button refreshButton = (Button) findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, CalculatorActivity.class));
            }
        });

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                WeatherWrapper.setTime();
                                textView.setText(WeatherWrapper.getTime().toString());
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "test-database").build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(WeatherController.getItemToDelete() != null){
                    db.databaseCityDAO().deleteByName(WeatherController.getItemToDelete());
                    WeatherController.setItemToDelete(null);
                }
            }
        }).start();
        TextView city_1_TextView = (TextView) findViewById(R.id.city_1_textView);
        System.out.println("Selected: " + WeatherController.getSelectedItem());
        final DatabaseCity[] selectedCity = {null};
        if(WeatherController.getSelectedItem() == -1)
            city_1_TextView.setText("No selection");
        else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    selectedCity[0] = db.databaseCityDAO().getAll().get(WeatherController.getSelectedItem());
                    }
            }).start();
        }
        city_1_TextView.setText("Wait...");
        try{
            Thread.sleep(500);
        }catch (InterruptedException e){

        }
        if(selectedCity[0] != null)
            city_1_TextView.setText(selectedCity[0].getCity_name() + " [" + selectedCity[0].getLatitude() + " : " + selectedCity[0].getLongitude() + "]");
        else
            city_1_TextView.setText("No selection");
    }
}