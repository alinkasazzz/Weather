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
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.RecyclerView.Adapter;
import com.example.weather.RecyclerView.Data;
import com.example.weather.databinding.CityListFragmentBinding;

import static com.example.weather.CityWeatherFragment.PARCEL;

public class CityListFragment extends Fragment {

    public static final String CURRENT_CITY = "currentCity";
    private Parcel currentParcel;
    private boolean isLandscape;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CityListFragmentBinding binding = CityListFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
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
    private void initRecyclerView(View view) {
        Data data = new Data(getResources().getStringArray(R.array.cities));

        RecyclerView recyclerView = view.findViewById(R.id.recycler_cities);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration decoration = new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.separator));
        recyclerView.addItemDecoration(decoration);

        Adapter adapter = new Adapter(Adapter.CITY_LIST, data);
        adapter.setClickable(v -> {
            TextView city = (TextView) v;
            currentParcel = new Parcel(city.getText().toString());
            showCityWeather(currentParcel);
        });
        recyclerView.setAdapter(adapter);
    }
}
