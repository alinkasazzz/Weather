package com.example.weather;

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

import com.example.weather.RecyclerView.Adapter;
import com.example.weather.RecyclerView.Data;
import com.example.weather.databinding.CityListFragmentBinding;

import java.util.regex.Pattern;

import static com.example.weather.CityWeatherFragment.PARCEL;

public class CityListFragment extends Fragment {
    public static final String CURRENT_CITY = "currentCity";
    private CityListFragmentBinding binding;
    private Data data;
    private Parcel currentParcel;
    private boolean isLandscape;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CityListFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        initSearch();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState != null) {
            currentParcel = (Parcel) savedInstanceState.getSerializable(CURRENT_CITY);
            showCityWeather(currentParcel);
        } else {
            currentParcel = new Parcel(getResources().getStringArray(R.array.cities)[0]);
        }
        if (isLandscape) {
            showCityWeather(currentParcel);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(CURRENT_CITY, currentParcel);
        super.onSaveInstanceState(outState);
    }


    private void showCityWeather(Parcel parcel) {
        assert getFragmentManager() != null;
        if (isLandscape) {
            CityWeatherFragment cityWeatherFragment = (CityWeatherFragment) getFragmentManager().findFragmentById(R.id.city_weather);
            if (cityWeatherFragment == null || !cityWeatherFragment.getParcel().getCityName().equals(parcel.getCityName())) {
                cityWeatherFragment = CityWeatherFragment.create(parcel);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.city_weather, cityWeatherFragment)
                        .commit();
            }
        } else {
            startActivity(new Intent(getContext(), CityWeatherActivity.class).putExtra(PARCEL, parcel));
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initRecyclerView() {
        data = new Data(getResources().getStringArray(R.array.cities));

        binding.recyclerCities.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerCities.setLayoutManager(layoutManager);

        DividerItemDecoration decoration = new DividerItemDecoration(binding.recyclerCities.getContext(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.separator));
        binding.recyclerCities.addItemDecoration(decoration);

        Adapter adapter = new Adapter(Adapter.CITY_LIST, data);
        adapter.setClickable(v -> {
            TextView city = (TextView) v;
            currentParcel = new Parcel(city.getText().toString());
            showCityWeather(currentParcel);
        });
        binding.recyclerCities.setAdapter(adapter);
    }

    private void initSearch() {
        Pattern pattern = Pattern.compile("^[А-Я][а-я]{2,}$");
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
            currentParcel = new Parcel(value);
            showCityWeather(currentParcel);
        } else showError(textView);

        if (value.equals("")) hideError(textView);
    }

    private boolean haveCity(String value) {
        for (int i = 0; i < data.getCities().length; i++) {
            if (value.equals(data.getCities()[i])) return true;
        }
        return false;
    }

    private void showError(TextView textView) {
        textView.setError("Город введён неверно");
    }

    private void hideError(TextView textView) {
        textView.setError(null);
    }
}
