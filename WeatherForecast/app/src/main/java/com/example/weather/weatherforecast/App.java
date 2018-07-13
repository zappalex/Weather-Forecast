package com.example.weather.weatherforecast;

import android.app.Application;

import com.example.weather.weatherforecast.dagger.AppModule;
import com.example.weather.weatherforecast.dagger.DaggerNetComponent;
import com.example.weather.weatherforecast.dagger.NetComponent;
import com.example.weather.weatherforecast.dagger.NetModule;

public class App extends Application {

    private static final String BASE_URL = "https://api.openweathermap.org/";
    private NetComponent netComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        netComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(BASE_URL))
                .build();
    }

    public NetComponent getNetComponent() {
        return netComponent;
    }
}
