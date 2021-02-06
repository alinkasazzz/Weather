package com.example.weather.CityWeather;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.weather.CityInfo.CityInfoFragment;
import com.example.weather.JSON.City;
import com.example.weather.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.regex.Pattern;

import static com.example.weather.CityWeather.CityWeatherFragment.CITY_KEY;

public class CityWeatherActivity extends AppCompatActivity {

    private ArrayList<City> citiesAll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_weather_activity);
        initToolbar();
        initCitiesList();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        if (savedInstanceState == null) {
            City city = (City) getIntent().getSerializableExtra(CITY_KEY);
            CityWeatherFragment cityWeatherFragment = CityWeatherFragment.create(city);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.city_weather, cityWeatherFragment)
                    .commit();
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.btn_info:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.city_weather, new CityInfoFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.btn_search:
                AlertDialog.Builder builder = new AlertDialog.Builder(CityWeatherActivity.this);
                final View contentView = getLayoutInflater().inflate(R.layout.search_dialog, null);
                final TextInputEditText editText = contentView.findViewById(R.id.search_text);
                builder.setView(contentView)
                        .setPositiveButton(R.string.searchDialog, (dialog, which) -> searchCity(editText));
                AlertDialog alert = builder.create();
                alert.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }

    private ArrayList<City> readCitiesJSON() {
        ArrayList<City> cities = new ArrayList<>();
        Gson gson = new Gson();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader
                    (this.getAssets().open("city.list.json")));
            Type listType = new TypeToken<ArrayList<City>>() {
            }.getType();
            cities = gson.fromJson(bufferedReader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }

    private void initCitiesList() {
        citiesAll = readCitiesJSON();
    }

    private void searchCity(TextView textView) {
        City city = null;
        Pattern pattern = Pattern.compile("^[A-Z][a-z]{2,}$");
        String value = textView.getText().toString().trim();
        if (pattern.matcher(value).matches() && haveCity(value)) {
            for (int i = 0; i < citiesAll.size(); i++) {
                if (citiesAll.get(i).getName().equals(value)) {
                    city = citiesAll.get(i);
                }
            }
        } else return;

        CityWeatherFragment cityWeatherFragment = CityWeatherFragment.create(city);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.city_weather, cityWeatherFragment)
                .commit();
    }

    private boolean haveCity(String value) {
        for (int i = 0; i < citiesAll.size(); i++) {
            if (value.equals(citiesAll.get(i).getName())) return true;
        }
        return false;
    }
}
