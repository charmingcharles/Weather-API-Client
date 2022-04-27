
package com.example.pogoda;

import android.os.AsyncTask;

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

public class ForecastDownloader extends AsyncTask<String, Void, String> {

    private static final String API_KEY = "efee5d88e8cbb8eaf5fadbddd4f39166";
    private static final String URL_CORE = "https://api.openweathermap.org/data/2.5/forecast/daily";

    private boolean isCity;

    private String cityName;

    private double latitude;
    private double longitude;

    private String[] icons = new String[3];

    private static boolean isCelsius = true;

    private ForecastCity forecastCity = new ForecastCity(0, 0, "");

    private int response;

    public static void setCelsius(boolean celsius) {
        ForecastDownloader.isCelsius = celsius;
    }

    public static boolean getCelsius() {
        return isCelsius;
    }

    public int getResponse() {
        return response;
    }

    private AppDatabase database;

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    public ForecastCity getForecastCity() {
        return forecastCity;
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

    public String[] getIcons() {
        return icons;
    }

    public void setIcons(String[] icons) {
        this.icons = icons;
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();
        String urlString;
        if(isCity)
            urlString = URL_CORE + "?q=" + cityName + "&units=" + (isCelsius ? "metric" : "standard") + "&cnt=3&appid=" + API_KEY;
        else
            urlString = URL_CORE + "?lat=" + latitude + "&lon=" + longitude + "&units=" + (isCelsius ? "metric" : "standard") + "&cnt=3&appid=" + API_KEY;
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
            String latitudeInfo = jsonObject.getJSONObject("city").getJSONObject("coord").getString("lat");
            String longitudeInfo = jsonObject.getJSONObject("city").getJSONObject("coord").getString("lon");
            String nameInfo = jsonObject.getJSONObject("city").getString("name");

            forecastCity.setName(nameInfo);
            forecastCity.setLatitude(Double.parseDouble(latitudeInfo));
            forecastCity.setLongitude(Double.parseDouble(longitudeInfo));

            System.out.println(latitudeInfo + " " + longitudeInfo + " " + nameInfo);

            for(int i = 0; i < 3; i++){
                String main = jsonObject.getJSONArray("list").getString(i);
                JSONObject object1 = new JSONObject(main);

                String day = "Day: " + object1.getJSONObject("temp").getString("day")  + "\n";

                String min = object1.getJSONObject("temp").getString("min");
                String max = object1.getJSONObject("temp").getString("max");

                String minMax = "Min/Max: " + min + "/" + max  + "\n";
                String night = "Night: " + object1.getJSONObject("temp").getString("night") + "\n";

                String pressure = "Pressure" +  object1.getString("pressure")  + "\n";
                String humidity = "Humidity" + object1.getString("humidity") + "\n";

                String listElementJson = jsonObject.getJSONArray("list").getString(0);
                JSONObject listObject = new JSONObject(listElementJson);

                String weatherJson = listObject.getJSONArray("weather").getString(0);
                JSONObject weatherObject = new JSONObject(weatherJson);
                String mainWeather = weatherObject.getString("main");
                String description = weatherObject.getString("description");

                String weather = "Weather: " + mainWeather + ", " + description + "\n";

                forecastCity.getDaysInfo()[i] = day + minMax + night + pressure + humidity + weather;

            }

            //String main = jsonObject.getString("list");
//            main = main.
//                    replace("{", "").
//                    replace("}", "").
//                    replace("\"", " ").
//                    replace(",", "\n");

            //System.out.println("_DAY_" + day);

            forecastCity.setDay1Info(forecastCity.getDaysInfo()[0]);
            forecastCity.setDay2Info(forecastCity.getDaysInfo()[1]);
            forecastCity.setDay3Info(forecastCity.getDaysInfo()[2]);
            //forecastCity.setMainInfo(main);

            //System.out.println(main);

            for(int i = 0; i < 3; i++){
                String listJson = jsonObject.getJSONArray("list").getString(i);
                JSONObject object2 = new JSONObject(listJson);
                String iconJson = object2.getJSONArray("weather").getString(0);
                JSONObject object3 = new JSONObject(iconJson);
                icons[i] = object3.getString("icon");
                System.out.println(icons[i]);
            }

//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    if(response < 400 && database != null){
//                        List<DatabaseCity> cities = database.databaseCityDAO().getAll();
//                        boolean isThereCity = false;
//                        for(DatabaseCity city : cities){
//                            if (city.getCity_name().equals(weatherCity.getName())) {
//                                isThereCity = true;
//                                break;
//                            }
//                        }
//                        if(!isThereCity){
//                            database.databaseCityDAO().insert(new DatabaseCity(weatherCity));
//                            WeatherController.setFavouriteCity(weatherCity, 0);
//                            WeatherWrapper.setLatitude(weatherCity.getLatitude());
//                            WeatherWrapper.setLongitude(weatherCity.getLongitude());
//                        }
//                    }
//                }
//            }).start();

//            if(response < 400 && databaseHandler != null){
//                List<City> cities = databaseHandler.getAllCities();
//                boolean isThereCity = false;
//                for(City city : cities){
//                    if (city.getName().equals(weatherCity.getName())) {
//                        isThereCity = true;
//                        break;
//                    }
//                }
//                if(!isThereCity){
//                    databaseHandler.addCity(weatherCity);
//                    WeatherController.setFavouriteCity(weatherCity, 0);
//                    WeatherWrapper.setLatitude(weatherCity.getLatitude());
//                    WeatherWrapper.setLongitude(weatherCity.getLongitude());
//                }
//            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
