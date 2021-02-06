package com.example.weather.RecyclerView;

import com.example.weather.JSON.City;

import java.util.ArrayList;

public class Data {

    private ArrayList<City> cities;
    private String[] times;
    private String[] icons;
    private float[] cityTemperatures;
    private final int size;

    public Data(ArrayList<City> cities) {
        this.cities = cities;
        size = cities.size();
    }

    public Data(String[] times, String[] icons, float[] cityTemperature, int size) {
        this.times = times;
        this.icons = icons;
        this.cityTemperatures = cityTemperature;
        this.size = size;
    }

    public Data(String[] times, String[] icons, float[] cityTemperature) {
        this.times = times;
        this.icons = icons;
        this.cityTemperatures = cityTemperature;
        this.size = times.length;
    }

    public int getSize() {
        return size;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public String[] getTimes() {
        return times;
    }

    public float[] getCityTemperatures() {
        return cityTemperatures;
    }

    public String[] getIcons() {
        return icons;
    }
}
