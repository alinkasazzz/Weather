package com.example.weather.CityList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.weather.CityWeather.CityWeatherActivity;
import com.example.weather.CityWeather.CityWeatherFragment;
import com.example.weather.JSON.City;
import com.example.weather.R;
import com.example.weather.RecyclerView.Adapter;
import com.example.weather.RecyclerView.Data;
import com.example.weather.databinding.CityListFragmentBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Pattern;

import static com.example.weather.CityWeather.CityWeatherFragment.CITY_KEY;

public class CityListFragment extends Fragment {
    public static final String CURRENT_CITY = "currentCity";
    private CityListFragmentBinding binding;
    private City currentCity;
    private boolean isLandscape;
    private ArrayList<City> citiesRU;
    private ArrayList<City> citiesAll;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CityListFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initCitiesList();
        initRecyclerView();
        initSearch();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState != null) {
            currentCity = (City) savedInstanceState.getSerializable(CURRENT_CITY);
            showCityWeather(currentCity);
        } else currentCity = citiesRU.get(0);

        if (isLandscape) {
            showCityWeather(currentCity);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(CURRENT_CITY, currentCity);
        super.onSaveInstanceState(outState);
    }


    private void showCityWeather(City city) {
        assert getFragmentManager() != null;
        if (isLandscape) {
            CityWeatherFragment cityWeatherFragment = (CityWeatherFragment) getFragmentManager().findFragmentById(R.id.city_weather);
            if (cityWeatherFragment == null || !cityWeatherFragment.getCity().getName().equals(city.getName())) {
                cityWeatherFragment = CityWeatherFragment.create(city);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.city_weather, cityWeatherFragment)
                        .commit();
            }
        } else {
            startActivity(new Intent(getContext(), CityWeatherActivity.class).putExtra(CITY_KEY, city));
        }
    }

    private ArrayList<City> readCitiesJSON(String fileName) {
        ArrayList<City> cities = new ArrayList<>();
        Gson gson = new Gson();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader
                    (Objects.requireNonNull(getContext()).getAssets().open(fileName)));
            Type listType = new TypeToken<ArrayList<City>>() {
            }.getType();
            cities = gson.fromJson(bufferedReader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }

    private void initCitiesList() {
        citiesAll = readCitiesJSON("city.list.json");
        citiesRU = readCitiesJSON("ru_cities.json");
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initRecyclerView() {
        Data data = new Data(citiesRU);

        binding.recyclerCities.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerCities.setLayoutManager(layoutManager);

        DividerItemDecoration decoration = new DividerItemDecoration(binding.recyclerCities.getContext(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.separator));
        binding.recyclerCities.addItemDecoration(decoration);

        Adapter adapter = new Adapter(Adapter.CITY_LIST, data);
        adapter.setClickable(v -> {
            TextView cityName = (TextView) v;
            findCityToShow(cityName.getText().toString());

        });
        binding.recyclerCities.setAdapter(adapter);
    }

    private void initSearch() {
        Pattern pattern = Pattern.compile("^[A-Z][a-z]{2,}$");
        binding.searchText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) return;
            TextView search = (TextView) v;
            validate(search, pattern);
        });

    }

    private void validate(TextView textView, Pattern pattern) {
        String value = textView.getText().toString().trim();
        if (pattern.matcher(value).matches() && haveCity(value)) {
            hideError(textView);
            findCityToShow(value);
        } else showError(textView);

        if (value.equals("")) hideError(textView);
    }

    private boolean haveCity(String value) {
        for (int i = 0; i < citiesAll.size(); i++) {
            if (value.equals(citiesAll.get(i).getName())) return true;
        }
        return false;
    }

    private void findCityToShow(String value) {
        City city;
        for (int i = 0; i < citiesAll.size(); i++) {
            if (citiesAll.get(i).getName().equals(value)) {
                city = citiesAll.get(i);
                currentCity = city;
                showCityWeather(city);
                break;
            }
        }
    }

    private void showError(TextView textView) {
        textView.setError("Город введён неверно");
    }

    private void hideError(TextView textView) {
        textView.setError(null);
    }
}
