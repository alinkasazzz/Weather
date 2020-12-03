package com.example.weather.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;

public class Adapter extends RecyclerView.Adapter<Holder> {

    public static final String CITY_LIST = "CityList";
    public static final String CITY_WEATHER = "CityWeather";

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
        if (fragment.equals(CITY_LIST)) {
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false));
        } else if (fragment.equals(CITY_WEATHER)) {
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false));
        }
        return new Holder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if (fragment.equals(CITY_LIST)) {
            holder.setData(data.getCities());
            holder.setOnClickListener(holder, clickable);
        } else if (fragment.equals(CITY_WEATHER)) {
            holder.setData(data.getCityDays(), data.getStatusImages(), data.getCityTemperatures());
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
