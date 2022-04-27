package com.example.pogoda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

public class MoonFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(
                R.layout.second_item_view_pager, container, false);
        TextView textView = (TextView) view.findViewById(R.id.moonText);
        TextView moonriseTextView = (TextView) view.findViewById(R.id.moonriseText);
        TextView moonsetTextTextView = (TextView) view.findViewById(R.id.moonsetText);
        TextView ageTextView = (TextView) view.findViewById(R.id.ageText);
        TextView illuminationTextView = (TextView) view.findViewById(R.id.illuminationText);
        TextView nextNewMoonTextView = (TextView) view.findViewById(R.id.nextNewMoonText);
        TextView nextFullMoonTextTextView = (TextView) view.findViewById(R.id.nextFullMoonText);

        String ratioString = PreferenceManager.getDefaultSharedPreferences(view.getContext()).getString("edit_text_preference_1", "5");
        int ratio = Integer.parseInt(ratioString);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep((ratio + 1) * 1000);
                        WeatherWrapper.setTime();
                        textView.setText("Current time: " + WeatherWrapper.getTime().toString());
                        moonriseTextView.setText("Moon rise: " + WeatherWrapper.getMoonrise().toString());
                        moonsetTextTextView.setText("Moon set: " + WeatherWrapper.getMoonset().toString());
                        ageTextView.setText("Age: " + WeatherWrapper.getAge());
                        illuminationTextView.setText("Illumination: " + WeatherWrapper.getIllumination());
                        nextNewMoonTextView.setText("Next New Moon: " + WeatherWrapper.getNextNewMoon());
                        nextFullMoonTextTextView.setText("Next Full Moon: " + WeatherWrapper.getNextFullMoon());
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