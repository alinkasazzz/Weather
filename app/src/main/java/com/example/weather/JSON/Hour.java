package com.example.weather.JSON;

import java.util.List;

public class Hour {
    private long dt;
    private List<WeatherIcons> weather;
    private float temp;

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public List<WeatherIcons> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherIcons> weather) {
        this.weather = weather;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }
}
