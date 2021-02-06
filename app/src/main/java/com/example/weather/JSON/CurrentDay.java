package com.example.weather.JSON;

import java.util.List;

public class CurrentDay {
    private long dt;
    private float temp;
    private List<WeatherIcons> weather;

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public List<WeatherIcons> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherIcons> weather) {
        this.weather = weather;
    }
}
