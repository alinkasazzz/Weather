package com.example.weather.JSON;

import java.util.List;

public class Day {
    private long dt;
    private Temperature temp;
    private List<WeatherIcons> weather;

    public Temperature getTemp() {
        return temp;
    }

    public void setTemp(Temperature temp) {
        this.temp = temp;
    }

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
}
