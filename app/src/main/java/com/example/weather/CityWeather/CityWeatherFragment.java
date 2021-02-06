package com.example.weather.CityWeather;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.BuildConfig;
import com.example.weather.JSON.City;
import com.example.weather.JSON.WeatherPOJO;
import com.example.weather.R;
import com.example.weather.RecyclerView.Adapter;
import com.example.weather.RecyclerView.Data;
import com.example.weather.databinding.CityWeatherFragmentBinding;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

public class CityWeatherFragment extends Fragment {

    public static final String CITY_KEY = "city_key";
    private static final String TAG = "WeatherError";
    private static final String exclude = "minutely,alerts";
    private static final String unitsOfMeasurement = "metric";
    private static final String language = "ru";
    private static final int daysLength = 7;
    private static final int hoursLength = 24;
    public static String cityName;
    private static String[] times;
    private static String[] icons;
    private static float[] temperatures;
    private CityWeatherFragmentBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CityWeatherFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolBar();
        loadForecast();
    }

    public static CityWeatherFragment create(City city) {
        CityWeatherFragment fragment = new CityWeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(CITY_KEY, city);
        fragment.setArguments(args);
        return fragment;
    }

    public City getCity() {
        assert getArguments() != null;
        return (City) getArguments().getSerializable(CITY_KEY);
    }

    private void loadForecast() {
        float lat = getCity().getCoord().getLat();
        float lon = getCity().getCoord().getLon();
        try {
            final String url =
                    String.format("https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&exclude=%s&units=%s&lang=%s&appid=%s",
                            lat,
                            lon,
                            exclude,
                            unitsOfMeasurement,
                            language,
                            BuildConfig.WEATHER_API_KEY_DEBUG
                    );
            final URL uri = new URL(url);
            final Handler handler = new Handler();

            new Thread(() -> {
                HttpsURLConnection urlConnection = null;
                try {
                    urlConnection = (HttpsURLConnection) uri.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setReadTimeout(10000);

                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String result = getLines(in);

                    Gson gson = new Gson();
                    final WeatherPOJO weatherPOJO = gson.fromJson(result, WeatherPOJO.class);
                    handler.post(() -> {
                        setWeatherCurrent(weatherPOJO);
                        initRecyclerHourly(weatherPOJO);
                        initRecyclerDaily(weatherPOJO);
                    });
                } catch (Exception e) {
                    Log.e(TAG, "Ошибка подключения", e);
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            }).start();
        } catch (MalformedURLException e) {
            Log.e(TAG, "Ошибка получения прогноза", e);
            e.printStackTrace();
        }
    }

    private void initToolBar() {
        cityName = getCity().getName();
        ActionBar actionBar = ((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(cityName);
    }

    private void initRecyclerDaily(WeatherPOJO weatherPOJO) {
        setTimesDaily(weatherPOJO);
        setWeatherIconsDaily(weatherPOJO);
        setTemperaturesDaily(weatherPOJO);
        Data data = new Data(times, icons, temperatures, daysLength - 1);
        RecyclerView recyclerView = binding.recyclerWeatherDaily;
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Adapter adapter = new Adapter(Adapter.CITY_WEATHER_DAILY, data);
        recyclerView.setAdapter(adapter);
    }

    private void initRecyclerHourly(WeatherPOJO weatherPOJO) {
        setTimesHourly(weatherPOJO);
        setWeatherIconsHourly(weatherPOJO);
        setTemperaturesHourly(weatherPOJO);
        Data data = new Data(times, icons, temperatures);
        RecyclerView recyclerView = binding.recyclerWeatherHourly;
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Adapter adapter = new Adapter(Adapter.CITY_WEATHER_HOURLY, data);
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("DefaultLocale")
    private void setWeatherCurrent(WeatherPOJO weatherPOJO) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("E, dd MMMM");
        Date date = new Date(weatherPOJO.getCurrent().getDt() * 1000L);

        String currentDay = format.format(date);
        String currentIcon = String.format("https://openweathermap.org/img/wn/%s@2x.png", weatherPOJO.getCurrent().getWeather().get(0).getIcon());
        String currentTemp = String.format("%d%s", (int) weatherPOJO.getCurrent().getTemp(), "°C");

        binding.currentDay.setText(currentDay);
        Picasso.get().load(currentIcon).fit().centerInside().placeholder(R.drawable.error).into(binding.currentStatus);
        binding.currentTemperature.setText(currentTemp);

    }

    private void setTimesHourly(WeatherPOJO weatherPOJO) {
        times = new String[hoursLength];
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        for (int i = 0; i < hoursLength; i++) {
            Date date = new Date(weatherPOJO.getHourly().get(i).getDt() * 1000L);
            times[i] = format.format(date);
        }
    }

    private void setWeatherIconsHourly(WeatherPOJO weatherPOJO) {
        icons = new String[hoursLength];
        for (int i = 0; i < hoursLength; i++) {
            icons[i] = String.format("https://openweathermap.org/img/wn/%s@2x.png", weatherPOJO.getHourly().get(i).getWeather().get(0).getIcon());
        }
    }

    private void setTemperaturesHourly(WeatherPOJO weatherPOJO) {
        temperatures = new float[hoursLength];
        for (int i = 0; i < hoursLength; i++) {
            temperatures[i] = weatherPOJO.getHourly().get(i).getTemp();
        }
    }

    private void setTimesDaily(WeatherPOJO weatherPOJO) {
        times = new String[daysLength];
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("E, dd MMMM");
        for (int i = 0; i < daysLength; i++) {
            Date date = new Date(weatherPOJO.getDaily().get(i + 1).getDt() * 1000L);
            times[i] = format.format(date);
        }
    }

    private void setWeatherIconsDaily(WeatherPOJO weatherPOJO) {
        icons = new String[daysLength];
        for (int i = 0; i < daysLength; i++) {
            icons[i] = String.format("https://openweathermap.org/img/wn/%s@2x.png", weatherPOJO.getDaily().get(i + 1).getWeather().get(0).getIcon());
        }
    }

    private void setTemperaturesDaily(WeatherPOJO weatherPOJO) {
        temperatures = new float[daysLength];
        for (int i = 0; i < daysLength; i++) {
            temperatures[i] = weatherPOJO.getDaily().get(i + 1).getTemp().getDay();
        }
    }

    private String getLines(BufferedReader bufferedReader) {
        StringBuilder stringBuilder = new StringBuilder();
        String currentLine;
        try {
            while ((currentLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(currentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}