package com.example.pogoda;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AdditionalWeatherFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdditionalWeatherFragment() {
        // Required empty public constructor
    }

    public static AdditionalWeatherFragment newInstance(String param1, String param2) {
        AdditionalWeatherFragment fragment = new AdditionalWeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_additional_weather, container, false);
        TextView textView = (TextView) view.findViewById(R.id.additional_weather_textView);
        textView.setText("Waiting...");
        try {
            Thread.sleep(1000);
            textView.setText(CalculatorActivity.downloader.getWeatherCity().getAdditionalInfo());
        }catch (InterruptedException e){
        }
        return view;
    }
}