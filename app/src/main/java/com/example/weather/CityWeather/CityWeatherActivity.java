package com.example.weather.CityWeather;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weather.JSON.City;
import com.example.weather.R;

import static com.example.weather.CityWeather.CityWeatherFragment.CITY_KEY;

public class CityWeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_weather_activity);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }

        if (savedInstanceState == null){
            City city = (City) getIntent().getSerializableExtra(CITY_KEY);
            CityWeatherFragment cityWeatherFragment = CityWeatherFragment.create(city);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.city_weather, cityWeatherFragment)
                    .commit();
        }
    }
}
