package com.example.pogoda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;
import androidx.room.Room;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class CalculatorActivity extends FragmentActivity {

    private static final int NUM_PAGES = 6;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    public static WeatherDownloader downloader;
    public static ForecastDownloader forecastDownloader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(WeatherController.getSelectedItem() != -1){
            AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "test-database").build();
            downloader = new WeatherDownloader();
            boolean result = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("unitsPreference", true);
            WeatherDownloader.setCelsius(result);
            downloader.setDatabase(db);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<DatabaseCity> cityList = db.databaseCityDAO().getAll();
                    if(cityList.size() > 0){
                        DatabaseCity city = cityList.get(WeatherController.getSelectedItem());
                        if(city.getCity_name().length() < 1)
                            downloader.setCoordsResponse(city.getLatitude(), city.getLongitude());
                        else
                            downloader.setNameResponse(city.getCity_name());
                        downloader.execute();

                        forecastDownloader = new ForecastDownloader();
                        boolean result = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("unitsPreference", true);
                        ForecastDownloader.setCelsius(result);
                        if(city.getCity_name().length() < 1)
                            forecastDownloader.setCoordsResponse(city.getLatitude(), city.getLongitude());
                        else
                            forecastDownloader.setNameResponse(city.getCity_name());
                        forecastDownloader.execute();
                    }
                }
            }).start();
        }
        System.out.println("???");
        setContentView(R.layout.view_pager_layout);
        WeatherWrapper.init();
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        FloatingActionButton settingsButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CalculatorActivity.this, SettingsActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            if(position == 0)
                return new SunFragment();
            else if(position == 1)
                return new MoonFragment();
            else if(position == 2)
                return new MainWeatherFragment();
            else if(position == 3)
                return new AdditionalWeatherFragment();
            else if(position == 4)
                return new ForecastFragment();
            else
                return new SettingsFragment();
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}