package com.example.weather.CityInfo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weather.CityWeather.CityWeatherFragment;
import com.example.weather.R;
import com.example.weather.databinding.CityInfoFragmentBinding;

public class CityInfoFragment extends Fragment {
    private CityInfoFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CityInfoFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWeb();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWeb() {
        binding.cityInfo.getSettings().setJavaScriptEnabled(true);
        binding.cityInfo.setWebViewClient(new WebViewClient());
        binding.cityInfo.loadUrl(getResources().getString(R.string.cityInfo_link) + CityWeatherFragment.cityName);
        binding.cityInfo.setOnKeyListener((v, keyCode, event) -> {
            WebView webView = (WebView) v;
            if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
            return false;
        });
    }
}
