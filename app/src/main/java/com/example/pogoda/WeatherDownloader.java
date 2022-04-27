
package com.example.pogoda;

import android.os.AsyncTask;

import androidx.preference.PreferenceManager;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.List;

public class WeatherDownloader extends AsyncTask<String, Void, String> {

    private static final String API_KEY = "efee5d88e8cbb8eaf5fadbddd4f39166";
    private static final String URL_CORE = "[my private api key]";

    private boolean isCity;

//    private int databaseIndex;
//    private DatabaseHandler databaseHandler;

    private String cityName;

    private double latitude;
    private double longitude;

    private static boolean isCelsius = true;

    private WeatherCity weatherCity = new WeatherCity(0, 0, "");

    private int response;

    public static void setCelsius(boolean celsius) {
        WeatherDownloader.isCelsius = celsius;
    }

    public static boolean getCelsius() {
        return isCelsius;
    }

//    public void setDatabase(int databaseIndex, DatabaseHandler databaseHandler) {
//        this.databaseIndex = databaseIndex;
//        this.databaseHandler = databaseHandler;
//    }

    public int getResponse() {
        return response;
    }

    public WeatherCity getWeatherCity() {
        return weatherCity;
    }

    public void setNameResponse(String cityName){
        this.cityName = cityName;
        this.isCity = true;
    }

    public void setCoordsResponse(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
        this.isCity = false;
    }

    private AppDatabase database;

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();
        String urlString;
        if(isCity)
            urlString = URL_CORE + "?q=" + cityName + "&units=" + (isCelsius ? "metric" : "standard") + "&appid=" + API_KEY;
        else
            urlString = URL_CORE + "?lat=" + latitude + "&lon=" + longitude + "&units=" + (isCelsius ? "metric" : "standard") + "&appid=" + API_KEY;
        try{
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            response = urlConnection.getResponseCode();
            System.out.println(response);
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(((line = reader.readLine()) != null)){
                stringBuilder.append(line).append("\n");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            System.out.println(s);
            JSONObject jsonObject = new JSONObject(s);
            String latitudeInfo = jsonObject.getJSONObject("coord").getString("lat");
            String longitudeInfo = jsonObject.getJSONObject("coord").getString("lon");
            String nameInfo = jsonObject.getString("name");

            weatherCity.setName(nameInfo);
            weatherCity.setLatitude(Double.parseDouble(latitudeInfo));
            weatherCity.setLongitude(Double.parseDouble(longitudeInfo));

            System.out.println(latitudeInfo + " " + longitudeInfo + " " + nameInfo);

            String main = jsonObject.getString("main");
            main = main.
                    replace("{", "").
                    replace("}", "").
                    replace("\"", " ").
                    replace(",", "\n").
                    replace("temp_min", "Min. temperature").
                    replace("temp_max", "Max. temperature").
                    replace("temp", "Temperature").
                    replace("feels_like", "Feels like").
                    replace("pressure", "Pressure").
                    replace("humidity", "Humidity");

            weatherCity.setMainInfo(main);

            String iconJson = jsonObject.getJSONArray("weather").getString(0);
            JSONObject object2 = new JSONObject(iconJson);
            String icon = object2.getString("icon");

            System.out.println(icon);

            JSONArray jsonArray = jsonObject.getJSONArray("weather");
            for(int i = 0; i < jsonArray.length(); i++){
                System.out.println("Value : " + jsonArray.get(i));
            }

            System.out.println("Icon: " + icon);

            weatherCity.setMainIcon(icon);

            System.out.println(main);

            String visibility = "Visibility :" + jsonObject.getString("visibility");
            String windSpeed = "Wind speed :"  + jsonObject.getJSONObject("wind").getString("speed");
            String windDeg = "Wind degree :"  + jsonObject.getJSONObject("wind").getString("deg");
            String clouds = "Clouds :"  + jsonObject.getJSONObject("clouds").getString("all");

            String additional = visibility + "\n" + windSpeed + "\n" + windDeg + "\n" + clouds;

            weatherCity.setAdditionalInfo(additional);

            System.out.println(additional);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    if(response < 400 && database != null){
                        List<DatabaseCity> cities = database.databaseCityDAO().getAll();
                        boolean isThereCity = false;
                        for(DatabaseCity city : cities){
                            if ((city.getCity_name().equals(weatherCity.getName()) && weatherCity.getName().length() > 0) || (city.getLatitude() == weatherCity.getLatitude() && city.getLongitude() == weatherCity.getLongitude())){
                                isThereCity = true;
                                break;
                            }
                        }
                        if(!isThereCity){
                            database.databaseCityDAO().insert(new DatabaseCity(weatherCity));
                            WeatherController.setFavouriteCity(weatherCity, 0);
                            WeatherWrapper.setLatitude(weatherCity.getLatitude());
                            WeatherWrapper.setLongitude(weatherCity.getLongitude());
                        }
                    }
                }
            }).start();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
