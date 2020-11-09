package com.example.weather;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weather.databinding.CityWeatherFragmentBinding;

public class CityWeatherFragment extends Fragment {

    public static final String PARCEL = "parcel";
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
        initViews();
    }

    public static CityWeatherFragment create(Parcel parcel){
        CityWeatherFragment fragment = new CityWeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARCEL, parcel);
        fragment.setArguments(args);
        return fragment;
    }

    public Parcel getParcel(){
        assert getArguments() != null;
        return (Parcel) getArguments().getSerializable(PARCEL);
    }

    private void initViews() {
        binding.city.setText(getParcel().getCityName());
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


}