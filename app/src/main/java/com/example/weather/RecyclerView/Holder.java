package com.example.weather.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.JSON.City;
import com.example.weather.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Holder extends RecyclerView.ViewHolder {

    private final TextView city;
    private final TextView cityDay;
    private final TextView cityHour;
    private final ImageView statusDaily;
    private final ImageView statusHourly;
    private final TextView cityTemperatureDaily;
    private final TextView cityTemperatureHourly;
    private final LinearLayout dailyContainer;

    public Holder(@NonNull View itemView) {
        super(itemView);
        city = itemView.findViewById(R.id.city);
        cityDay = itemView.findViewById(R.id.city_day);
        cityHour = itemView.findViewById(R.id.city_hour);
        statusDaily = itemView.findViewById(R.id.status_daily);
        statusHourly = itemView.findViewById(R.id.status_hourly);
        cityTemperatureDaily = itemView.findViewById(R.id.city_temperature_daily);
        cityTemperatureHourly = itemView.findViewById(R.id.city_temperature_hourly);
        dailyContainer = itemView.findViewById(R.id.daily_container);
    }

    public TextView getCity() {
        return city;
    }

    public TextView getCityDay() {
        return cityDay;
    }

    public TextView getCityHour() {
        return cityHour;
    }

    public ImageView getStatusDaily() {
        return statusDaily;
    }

    public ImageView getStatusHourly() {
        return statusHourly;
    }

    public TextView getCityTemperatureDaily() {
        return cityTemperatureDaily;
    }

    public TextView getCityTemperatureHourly() {
        return cityTemperatureHourly;
    }

    public void setDataCities(ArrayList<City> cities) {
        getCity().setText(cities.get(getAdapterPosition()).getName());
    }

    @SuppressLint("DefaultLocale")
    public void setDataDaily(String[] times, String[] icons, float[] cityTemperatures) {
        getCityDay().setText(times[getAdapterPosition()]);
        Picasso.get().load(icons[getAdapterPosition()]).fit().centerInside().placeholder(R.drawable.error).into(getStatusDaily());
        getCityTemperatureDaily().setText(String.format("%d%s", (int) cityTemperatures[getAdapterPosition()], "°C"));
    }

    @SuppressLint("DefaultLocale")
    public void setDataHourly(String[] times, String[] icons, float[] cityTemperatures) {
        getCityHour().setText(times[getAdapterPosition()]);
        Picasso.get().load(icons[getAdapterPosition()]).fit().centerInside().placeholder(R.drawable.error).into(getStatusHourly());
        getCityTemperatureHourly().setText(String.format("%d%s", (int) cityTemperatures[getAdapterPosition()], "°C"));
    }

    public void setOnClickListener(Holder holder, Clickable clickable) {
        // "::" Функциональный интерфейс или consumer - интерфейс, который имеет только 1 абстрактный метод.
        holder.itemView.setOnClickListener(clickable::click);
    }

    public void adaptiveLandscape(View view) {
        if (view.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            dailyContainer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100));
        }
    }

}
