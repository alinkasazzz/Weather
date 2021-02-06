package com.example.weather.JSON;

import java.util.List;

public class WeatherPOJO {
    private List<Day> daily;
    private CurrentDay current;
    private List<Hour> hourly;

    public List<Day> getDaily() {
        return daily;
    }

    public void setDaily(List<Day> daily) {
        this.daily = daily;
    }

    public CurrentDay getCurrent() {
        return current;
    }

    public void setCurrent(CurrentDay current) {
        this.current = current;
    }

    public List<Hour> getHourly() {
        return hourly;
    }

    public void setHourly(List<Hour> hourly) {
        this.hourly = hourly;
    }
}
