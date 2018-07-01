package com.example.weather.weatherforecast.activities;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.weatherforecast.R;
import com.example.weather.weatherforecast.models.ForecastSingleDay;
import com.example.weather.weatherforecast.utilities.FieldFormatter;
import com.example.weather.weatherforecast.utilities.ImageMapper;

import org.w3c.dom.Text;

import java.lang.reflect.Field;


public class WeatherDetailActivity extends AppCompatActivity {

    private TextView dayOfWeekTv;
    private TextView dateTv;
    private TextView tempCurrentTv;
    private TextView tempMinTv;
    private ImageView weatherIv;
    private TextView conditionTv;
    private TextView humidityTv;
    private TextView pressureTv;
    private TextView windTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        initUiComponents();

        Intent intentThatStartedActivity = getIntent();
        ForecastSingleDay forecastDay = retrieveForecastObjFromIntent(intentThatStartedActivity);

        if(forecastDay != null){
            populateUiComponents(forecastDay);
        }
    }

    private ForecastSingleDay retrieveForecastObjFromIntent(Intent intentThatStartedActivity) {
        if (intentThatStartedActivity != null && intentThatStartedActivity.hasExtra(MainActivity.PARCELABLE_FORECAST_DAY)) {
            return intentThatStartedActivity.getParcelableExtra(MainActivity.PARCELABLE_FORECAST_DAY);
        } else {
            Toast.makeText(this, getString(R.string.error_intent_not_found), Toast.LENGTH_LONG).show();
            return null;
        }

    }

    private void initUiComponents() {
        dayOfWeekTv = (TextView) findViewById(R.id.day_of_week_tv);
        dateTv = (TextView) findViewById(R.id.date_tv);
        tempCurrentTv = (TextView) findViewById(R.id.temp_current_tv);
        tempMinTv = (TextView) findViewById(R.id.temp_min_tv);
        weatherIv = (ImageView) findViewById(R.id.weather_img);
        conditionTv = (TextView) findViewById(R.id.condition_tv);
        humidityTv = (TextView) findViewById(R.id.humidity_tv);
        pressureTv = (TextView) findViewById(R.id.pressure_tv);
        windTv = (TextView) findViewById(R.id.wind_tv);
    }

    private void populateUiComponents(ForecastSingleDay forecastDay) {
        dayOfWeekTv.setText(forecastDay.getDayOfWeek());
        dateTv.setText(FieldFormatter.getMonthAndDayStr(forecastDay.getMonth(), forecastDay.getDayOfMonth()));
        tempCurrentTv.setText(FieldFormatter.getFormattedTemp(forecastDay.getTemp()));
        tempMinTv.setText(FieldFormatter.getFormattedTemp(forecastDay.getMinTemp()));
        weatherIv.setImageResource(ImageMapper.getImageResourceId(forecastDay.getIcon()));
        conditionTv.setText(forecastDay.getConditionDesc());
        humidityTv.setText(FieldFormatter.getFormattedHumidity(forecastDay.getHumidity()));
        pressureTv.setText(FieldFormatter.getFormattedPressure(forecastDay.getPressure()));
        windTv.setText(FieldFormatter.getFormattedWind(forecastDay.getWindSpeed(), forecastDay.getWindDirection()));
    }

}
