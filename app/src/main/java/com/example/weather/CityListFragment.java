package com.example.weather;

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

import com.example.weather.databinding.CityListFragmentBinding;

import static com.example.weather.CityWeatherFragment.PARCEL;

public class CityListFragment extends Fragment {
    private CityListFragmentBinding binding;
    public static final String CURRENT_CITY = "currentCity";
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
        initViews();
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

    private void initViews() {
        String[] cities = getResources().getStringArray(R.array.cities);
        TextView[] citiesView = new TextView[]{
                binding.city0,
                binding.city1,
                binding.city2,
                binding.city3,
                binding.city4,
                binding.city5,
                binding.city6,
                binding.city7,
                binding.city8
        };
        for (int i = 0; i < citiesView.length; i++) {
            citiesView[i].setText(cities[i]);
            citiesView[i].setOnClickListener(v -> {
                TextView city = (TextView) v;
                currentParcel = new Parcel(city.getText().toString());
                showCityWeather(currentParcel);
            });
        }
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
}
