package com.example.weather;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weather.databinding.CityListBinding;

public class CitySearch extends AppCompatActivity {
    private CityListBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instanceCheck(savedInstanceState);
        binding = CityListBinding.inflate(getLayoutInflater());
        initViews();
        setContentView(binding.getRoot());
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
        String[] cities = getResources().getStringArray(R.array.cities);
        TextView[] citiesView = new TextView[]{
                binding.city0,
                binding.city1,
                binding.city2,
                binding.city3,
                binding.city4,
                binding.city5,
                binding.city6,
                binding.city7,
                binding.city8
        };
        for (int i = 0; i < citiesView.length; i++) {
            citiesView[i].setText(cities[i]);
            citiesView[i].setOnClickListener(v -> {
                TextView view = (TextView) v;
                Intent intent = new Intent(CitySearch.this, Weather.class);
                intent.putExtra("cityName", view.getText().toString());
                startActivity(intent);
            });
        }
    }

    private void instanceCheck(Bundle savedInstanceState) {
        String instanceState;
        if (savedInstanceState == null) {
            instanceState = "Первый запуск";
        } else instanceState = "Повторный запуск";
        Toast.makeText(getApplicationContext(),
                instanceState + " - активити создано",
                Toast.LENGTH_SHORT).show();
    }

    private void logLifeStage(String tag, String text) {
        Toast.makeText(getApplicationContext(),
                tag + " - " + text,
                Toast.LENGTH_SHORT).show();
        Log.d(tag, text);
    }
}
