package com.example.weather;

import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.RecyclerView.Adapter;
import com.example.weather.RecyclerView.Data;
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
        initRecyclerView(view);
    }

    public static CityWeatherFragment create(Parcel parcel) {
        CityWeatherFragment fragment = new CityWeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARCEL, parcel);
        fragment.setArguments(args);
        return fragment;
    }

    public Parcel getParcel() {
        assert getArguments() != null;
        return (Parcel) getArguments().getSerializable(PARCEL);
    }

    private void initViews(){
        binding.city.setText(getParcel().getCityName());
        binding.btnInfo.setOnClickListener(v -> {
            Uri uri = Uri.parse(getResources().getString(R.string.cityInfo_link) + getParcel().getCityName());
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        });
    }

    private void initRecyclerView(View view) {
        String[] days = getResources().getStringArray(R.array.days);
        TypedArray statuses = getResources().obtainTypedArray(R.array.images);
        String[] temperatures = getResources().getStringArray(R.array.temperatures);

        Data data = new Data(days, statuses, temperatures);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_weather);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        Adapter adapter = new Adapter(Adapter.CITY_WEATHER, data);
        recyclerView.setAdapter(adapter);
    }
}