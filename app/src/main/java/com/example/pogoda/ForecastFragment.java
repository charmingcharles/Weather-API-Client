package com.example.pogoda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ForecastFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ForecastFragment() {
        // Required empty public constructor
    }

    public static ForecastWeatherFragment newInstance(String param1, String param2) {
        ForecastWeatherFragment fragment = new ForecastWeatherFragment();
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
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_forecast, container, false);
        TextView forecastText1 = (TextView) view.findViewById(R.id.forecastText1);
        TextView forecastText2 = (TextView) view.findViewById(R.id.forecastText2);
        TextView forecastText3 = (TextView) view.findViewById(R.id.forecastText3);
        try {
            Thread.sleep(1000);
            forecastText1.setText(CalculatorActivity.forecastDownloader.getForecastCity().getDay1Info());
            forecastText2.setText(CalculatorActivity.forecastDownloader.getForecastCity().getDay2Info());
            forecastText3.setText(CalculatorActivity.forecastDownloader.getForecastCity().getDay3Info());
        }catch (InterruptedException e){
        }

        ImageView imageView1 = (ImageView) view.findViewById(R.id.iconView1);
        new DownloadImageTask(imageView1).execute("https://openweathermap.org/img/wn/" + CalculatorActivity.forecastDownloader.getIcons()[0] +".png");

        ImageView imageView2 = (ImageView) view.findViewById(R.id.iconView2);
        new DownloadImageTask(imageView2).execute("https://openweathermap.org/img/wn/" + CalculatorActivity.forecastDownloader.getIcons()[1] +".png");

        ImageView imageView3 = (ImageView) view.findViewById(R.id.iconView3);
        new DownloadImageTask(imageView3).execute("https://openweathermap.org/img/wn/" + CalculatorActivity.forecastDownloader.getIcons()[2] +".png");

        return view;
    }
}