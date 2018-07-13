package com.example.weather.weatherforecast.activities;

import android.content.Context;
import android.content.Intent;
import android.content.RestrictionEntry;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.weatherforecast.App;
import com.example.weather.weatherforecast.R;
import com.example.weather.weatherforecast.adapters.ForecastAdapter;
import com.example.weather.weatherforecast.dagger.RestApi;
import com.example.weather.weatherforecast.models.ForecastCurrent;
import com.example.weather.weatherforecast.models.ForecastDaily;
import com.example.weather.weatherforecast.models.ForecastSingleDay;
import com.example.weather.weatherforecast.network.JsonParser;
import com.example.weather.weatherforecast.network.NetworkCalls;
import com.example.weather.weatherforecast.utilities.FieldFormatter;
import com.example.weather.weatherforecast.utilities.ImageMapper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity implements ForecastAdapter.ForecastAdapterOnClickHandler{

    public static final String PARCELABLE_FORECAST_DAY = "forecast";
    private TextView dateTv;
    private TextView tempCurrentTv;
    private TextView tempMinTv;
    private ImageView weatherIv;
    private TextView conditionTv;
    private FrameLayout frameLayoutToday;

    private ForecastAdapter forecastAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((App) getApplication()).getNetComponent().inject(this);
        initUiComponents();

        fetchWeatherOnlyIfDeviceOnline();
    }

    private void initUiComponents(){
        dateTv = (TextView) findViewById(R.id.date_tv);
        tempCurrentTv = (TextView) findViewById(R.id.temp_current_tv);
        tempMinTv = (TextView) findViewById(R.id.temp_min_tv);
        weatherIv = (ImageView) findViewById(R.id.weather_img);
        conditionTv = (TextView) findViewById(R.id.condition_tv);
        frameLayoutToday = (FrameLayout)findViewById(R.id.frame_today);

        RecyclerView weatherDailyRv = (RecyclerView) findViewById(R.id.weather_daily_rv);
        layoutManager = new LinearLayoutManager(this);
        weatherDailyRv.setLayoutManager(layoutManager);

        forecastAdapter = new ForecastAdapter(this);
        weatherDailyRv.setAdapter(forecastAdapter);
    }

    private void fetchWeatherOnlyIfDeviceOnline(){
        if (NetworkCalls.isDeviceOnline(this)) {
            new FetchCurrentWeatherTask().execute();
            new FetchDailyWeatherTask().execute();
            makeGetCurrentForecastCall();
        }else{
            Toast.makeText(this, getString(R.string.error_device_offline), Toast.LENGTH_LONG).show();
        }
    }

    private void makeGetCurrentForecastCall(){

        Call<ForecastCurrent> forecast = retrofit.create(RestApi.class).getCurrentForecast();

        forecast.enqueue(new Callback<ForecastCurrent>() {
            @Override
            public void onResponse(Call<ForecastCurrent> call, Response<ForecastCurrent> response) {

            }

            @Override
            public void onFailure(Call<ForecastCurrent> call, Throwable t) {

            }
        });

    }

    // TODO : extract functionality into makeGetCurrentForecastCall
    private class FetchCurrentWeatherTask extends AsyncTask<Void, Void, ForecastSingleDay>{
        @Override
        protected ForecastSingleDay doInBackground(Void... params) {
            URL currentWeatherUrl = NetworkCalls.convertStringToUrl(NetworkCalls.URL_CURRENT_WEATHER);
            if (currentWeatherUrl != null ){
                try {
                    String weatherResults = NetworkCalls.getResponseFromHttpUrl(currentWeatherUrl);
                    return JsonParser.getForecastCurrentSingleDay(weatherResults);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ForecastSingleDay forecastToday) {
            super.onPostExecute(forecastToday);

            if( forecastToday != null){
                populateTodaysWeatherUi(forecastToday);
                registerOnClickTodaysWeather(forecastToday);
            }else{
                callErrorToastCurrentPayload();
            }
        }
    }

    private void callErrorToastCurrentPayload(){
        Toast.makeText(this, getString(R.string.error_network_day_weather), Toast.LENGTH_LONG).show();
    }

    private void populateTodaysWeatherUi( ForecastSingleDay forecastToday){
        dateTv.setText(FieldFormatter.getFullDateStr(forecastToday.getMonth(), forecastToday.getDayOfMonth()));
        tempCurrentTv.setText(FieldFormatter.getFormattedTemp(forecastToday.getTemp()));
        tempMinTv.setText(FieldFormatter.getFormattedTemp(forecastToday.getMinTemp()));
        weatherIv.setImageResource(ImageMapper.getImageResourceId(forecastToday.getIcon()));
        conditionTv.setText(forecastToday.getConditionDesc());
    }

    private void registerOnClickTodaysWeather(final ForecastSingleDay forecastToday){
        frameLayoutToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               navigateToWeatherDetailActivity(forecastToday);
            }
        });
    }

    private void navigateToWeatherDetailActivity(ForecastSingleDay forecastSingleDay){
        Context context = this;
        Class destinationActivity = WeatherDetailActivity.class;
        Intent intentStartWeatherDetail = new Intent(context, destinationActivity);
        intentStartWeatherDetail.putExtra(PARCELABLE_FORECAST_DAY, forecastSingleDay);

        startActivity(intentStartWeatherDetail);
    }

    private void makeGetDailyForecastCall(){

        Call<List<ForecastDaily>> forecast = retrofit.create(RestApi.class).getDailyForecast();

        forecast.enqueue(new Callback<List<ForecastDaily>>() {
            @Override
            public void onResponse(Call<List<ForecastDaily>> call, Response<List<ForecastDaily>> response) {

            }

            @Override
            public void onFailure(Call<List<ForecastDaily>> call, Throwable t) {

            }
        });

    }

    // TODO : extract funtionality in to makeGetDailyForecastCall
    private class FetchDailyWeatherTask extends AsyncTask<Void, Void, ArrayList<ForecastSingleDay>>{
        @Override
        protected ArrayList<ForecastSingleDay> doInBackground(Void... params) {
            URL currentWeatherUrl = NetworkCalls.convertStringToUrl(NetworkCalls.URL_DAILY_WEATHER);
            if (currentWeatherUrl != null ){
                try {
                    String weatherResults = NetworkCalls.getResponseFromHttpUrl(currentWeatherUrl);
                    return JsonParser.getForecastDailyList(weatherResults);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<ForecastSingleDay> forecastDailyList) {
            super.onPostExecute(forecastDailyList);

            if(forecastDailyList != null){
                // replace with tomorrow text here.
                forecastDailyList.get(0).setDayOfWeek("Tomorrow");
                forecastAdapter.setForecastList(forecastDailyList);
            }else{
                callErrorToastWeekPayload();
            }
        }
    }

    private void callErrorToastWeekPayload(){
        Toast.makeText(this, getString(R.string.error_network_week_weather), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(ForecastSingleDay forecast) {
        navigateToWeatherDetailActivity(forecast);
    }
}
