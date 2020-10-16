package com.example.weather;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weather.databinding.CityWeatherBinding;

public class Weather extends AppCompatActivity {

    private CityWeatherBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CityWeatherBinding.inflate(getLayoutInflater());
        initViews();
        setContentView(binding.getRoot());
        logLifeStage("onCreate", "создание активити");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        logLifeStage("onRestoreInstanceState", "смена ориентации экрана");
    }

    @Override
    protected void onStop() {
        super.onStop();
        logLifeStage("onStop", "остановка предыдущей активити");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logLifeStage("onRestart", "перезапуск активити");
    }

    private void initViews() {
        binding.city.setText(getIntent().getStringExtra("cityName"));
        String[] days = getResources().getStringArray(R.array.days);
        TextView[] daysView = new TextView[]{
                binding.monday,
                binding.tuesday,
                binding.wednesday,
                binding.thursday,
                binding.friday,
                binding.saturday,
                binding.sunday
        };
        String[] temp = getResources().getStringArray(R.array.temperature);
        TextView[] tempView = new TextView[]{
                binding.temp1,
                binding.temp2,
                binding.temp3,
                binding.temp4,
                binding.temp5,
                binding.temp6,
                binding.temp7
        };
        for (int i = 0; i < 7; i++) {
            daysView[i].setText(days[i]);
            tempView[i].setText(temp[i]);
        }

        binding.btnInfo.setOnClickListener(v -> {
            Uri uri = Uri.parse(getResources().getString(R.string.cityInfo_link) + binding.city.getText().toString());
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        });
    }

    private void logLifeStage(String tag, String text) {
        Toast.makeText(getApplicationContext(),
                tag + " - " + text,
                Toast.LENGTH_SHORT).show();
        Log.d(tag, text);
    }
}