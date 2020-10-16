package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.databinding.CityListBinding;
import com.example.weather.databinding.CityWeatherBinding;

public class MainActivity extends AppCompatActivity {
    private CityListBinding bindingCities;
    private CityWeatherBinding bindingWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingCities = CityListBinding.inflate(getLayoutInflater());
        bindingWeather = CityWeatherBinding.inflate(getLayoutInflater());
        initViews();
        setContentView(bindingWeather.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(getApplicationContext(), "onRestoreInstanceState", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(), "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle saveInsanceState) {
        super.onSaveInstanceState(saveInsanceState);
        Toast.makeText(getApplicationContext(), "onSaveInstanceState", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(), "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(getApplicationContext(), "onRestart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "onDestroy", Toast.LENGTH_SHORT).show();
    }

    private void initViews() {
        String[] cities = getResources().getStringArray(R.array.cities);
        TextView[] citiesView = new TextView[]{
                bindingCities.city0,
                bindingCities.city1,
                bindingCities.city2,
                bindingCities.city3,
                bindingCities.city4,
                bindingCities.city5,
                bindingCities.city6,
                bindingCities.city7,
                bindingCities.city8
        };
        for (int i = 0; i < citiesView.length; i++) {
            citiesView[i].setText(cities[i]);
        }
        String[] days = getResources().getStringArray(R.array.days);
        TextView[] daysView = new TextView[]{
                bindingWeather.monday,
                bindingWeather.tuesday,
                bindingWeather.wednesday,
                bindingWeather.thursday,
                bindingWeather.friday,
                bindingWeather.saturday,
                bindingWeather.sunday
        };
        String[] temp = getResources().getStringArray(R.array.temperature);
        TextView[] tempView = new TextView[]{
                bindingWeather.temp1,
                bindingWeather.temp2,
                bindingWeather.temp3,
                bindingWeather.temp4,
                bindingWeather.temp5,
                bindingWeather.temp6,
                bindingWeather.temp7
        };
        for (int i = 0; i < 7; i++) {
            daysView[i].setText(days[i]);
            tempView[i].setText(temp[i]);
        }
    }
}