package com.example.weather;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.weather.CityWeatherFragment.PARCEL;

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
            Parcel parcel = (Parcel) getIntent().getSerializableExtra(PARCEL);
            CityWeatherFragment cityWeatherFragment = CityWeatherFragment.create(parcel);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.city_weather, cityWeatherFragment)
                    .commit();
        }
    }
}
