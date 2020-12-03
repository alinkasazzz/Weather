package com.example.weather.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;

public class Holder extends RecyclerView.ViewHolder {

    private final TextView city;
    private final TextView cityDay;
    private final ImageView statusImg;
    private final TextView cityTemperature;

    public Holder(@NonNull View itemView) {
        super(itemView);
        city = itemView.findViewById(R.id.city);
        cityDay = itemView.findViewById(R.id.city_day);
        statusImg = itemView.findViewById(R.id.status_img);
        cityTemperature = itemView.findViewById(R.id.city_temperature);

    }

    public TextView getCity() {
        return city;
    }

    public TextView getCityDay() {
        return cityDay;
    }

    public ImageView getStatusImg() {
        return statusImg;
    }

    public TextView getCityTemperature() {
        return cityTemperature;
    }

    public void setData(String[] cities) {
        getCity().setText(cities[getAdapterPosition()]);
    }

    public void setData(String[] cityDays, int[] statusImages, String[] cityTemperatures) {
        getCityDay().setText(cityDays[getAdapterPosition()]);
        getStatusImg().setImageResource(statusImages[getAdapterPosition()]);
        getCityTemperature().setText(cityTemperatures[getAdapterPosition()]);
    }

    public void setOnClickListener(Holder holder, Clickable clickable) {
        // "::" Функциональный интерфейс или consumer - интерфейс, который имеет только 1 абстрактный метод.
        holder.itemView.setOnClickListener(clickable::click);
    }
}
