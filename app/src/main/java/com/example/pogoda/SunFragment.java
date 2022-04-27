package com.example.pogoda;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

public class SunFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(
                R.layout.item_view_pager, container, false);
        TextView textView = (TextView) view.findViewById(R.id.sunText);
        TextView sunriseTextView = (TextView) view.findViewById(R.id.sunsriseText);
        TextView twilightMorningTextView = (TextView) view.findViewById(R.id.twilightMorningText);
        TextView twilightEveningTextView = (TextView) view.findViewById(R.id.twilightEveningText);
        TextView azimuthSetTextView = (TextView) view.findViewById(R.id.azimuthSetText);
        TextView azimuthRiseTextView = (TextView) view.findViewById(R.id.azimuthRiseText);
        TextView sunsetTextView = (TextView) view.findViewById(R.id.sunsetText);
        String ratioString = PreferenceManager.getDefaultSharedPreferences(view.getContext()).getString("edit_text_preference_1", "5");
        int ratio = Integer.parseInt(ratioString);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep((ratio + 1) * 1000);
                        WeatherWrapper.setTime();
                        WeatherWrapper.setLatitude((int) CalculatorActivity.downloader.getWeatherCity().getLatitude());
                        WeatherWrapper.setLongitude((int) CalculatorActivity.downloader.getWeatherCity().getLongitude());
                        textView.setText("Current time: " + WeatherWrapper.getTime().toString());
                        sunriseTextView.setText("Sunrise: " + WeatherWrapper.getSunrise().toString());
                        twilightMorningTextView.setText("Twilight Morning: " + WeatherWrapper.getTwilightMorning().toString());
                        twilightEveningTextView.setText("Twilight Evening: " + WeatherWrapper.getTwilightEvening().toString());
                        azimuthSetTextView.setText("Azimuth Set: " + WeatherWrapper.getAzimuthSet());
                        azimuthRiseTextView.setText("Azimuth Rise: " + WeatherWrapper.getAzimuthRise());
                        sunsetTextView.setText("Sunset: " + WeatherWrapper.getSunset());
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        thread.start();
        textView.setText("Waiting...");
        return view;
    }
}