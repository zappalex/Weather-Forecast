package com.example.weather.weatherforecast.dagger;

import com.example.weather.weatherforecast.models.ForecastCurrent;
import com.example.weather.weatherforecast.models.ForecastDaily;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

// TODO : add paths
public interface RestApi {
    @GET("/data/2.5/weather?id=4180439&units=imperial&appid=af52ca12a8a7a4ea06c2b16fd61448df")
    Call<ForecastCurrent> getCurrentForecast();

    @GET("/data/2.5/forecast/daily?id=4180439&units=imperial&cnt=6&appid=af52ca12a8a7a4ea06c2b16fd61448df")
    Call<List<ForecastDaily>> getDailyForecast();
}
