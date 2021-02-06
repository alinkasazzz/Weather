package com.example.weather.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;

public class Adapter extends RecyclerView.Adapter<Holder> {

    public static final String CITY_LIST = "CityList";
    public static final String CITY_WEATHER_DAILY = "CityWeatherDaily";
    public static final String CITY_WEATHER_HOURLY = "CityWeatherHourly";

    private final String fragment;
    private final Data data;
    private Clickable clickable;

    public Adapter(String fragment, Data data) {
        this.fragment = fragment;
        this.data = data;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (fragment) {
            case CITY_LIST:
                return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false));
            case CITY_WEATHER_DAILY:
                return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_daily, parent, false));
            case CITY_WEATHER_HOURLY:
                return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_hourly, parent, false));
        }
        return new Holder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        switch (fragment) {
            case CITY_LIST:
                holder.setDataCities(data.getCities());
                holder.setOnClickListener(holder, clickable);
                break;
            case CITY_WEATHER_DAILY:
                holder.setDataDaily(data.getTimes(), data.getIcons(), data.getCityTemperatures());
                holder.adaptiveLandscape(holder.itemView);
                break;
            case CITY_WEATHER_HOURLY:
                holder.setDataHourly(data.getTimes(), data.getIcons(), data.getCityTemperatures());
                break;
        }
    }


    @Override
    public int getItemCount() {
        return data.getSize();
    }

    public void setClickable(Clickable clickable) {
        this.clickable = clickable;
    }
}
