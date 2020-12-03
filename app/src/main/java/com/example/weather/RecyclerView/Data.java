package com.example.weather.RecyclerView;

import android.content.res.TypedArray;

public class Data {

    private String[] cities;
    private String[] cityDays;
    private TypedArray statusImages;
    private String[] cityTemperatures;
    private final int size;

    public Data(String[] cities) {
        this.cities = cities;
        size = cities.length;
    }

    public Data(String[] cityDays, TypedArray statusImg, String[] cityTemperature) {
        this.cityDays = cityDays;
        this.statusImages = statusImg;
        this.cityTemperatures = cityTemperature;
        size = cityDays.length;
    }

    public int getSize() {
        return size;
    }

    public String[] getCities() {
        return cities;
    }

    public String[] getCityDays() {
        return cityDays;
    }

    public int[] getStatusImages() {
        int length = statusImages.length();
        int[] answer = new int[length];
        for (int i = 0; i < length; i++) {
            answer[i] = statusImages.getResourceId(i, 0);
        }
        return answer;
    }

    public String[] getCityTemperatures() {
        return cityTemperatures;
    }
}
