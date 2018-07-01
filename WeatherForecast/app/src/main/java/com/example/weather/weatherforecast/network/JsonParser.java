package com.example.weather.weatherforecast.network;

import com.example.weather.weatherforecast.utilities.FieldFormatter;
import com.example.weather.weatherforecast.models.ForecastSingleDay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Note for parseDailySingleDay() and parseCurrentSingleDay():
 * <p>
 * If there is no data for a field, the api will leave out the key-value pair entirely.  If a value is not found, an exception
 * will be thrown for the entire object.  Therefore, for the non-important extra fields used in the detail activity,
 * we are checking for the value and defaulting if not found.  For the fields used in main activity, we simply
 * let the exception get thrown and consider it an incomplete payload.
 * <p>
 * These non-important fields are : humidity, pressure, (wind) speed, (wind) direction
 */

public class JsonParser {

    private static final String DATE = "dt";
    private static final String MAIN_OBJ = "main";
    private static final String TEMP = "temp";
    private static final String TEMP_MAX = "temp_max";
    private static final String TEMP_MIN = "temp_min";
    private static final String DAY = "day";
    private static final String MIN = "min";
    private static final String MAX = "max";
    private static final String WEATHER_OBJ = "weather";
    private static final String WEATHER_DESC = "main";
    private static final String WEATHER_ICON = "icon";
    private static final String HUMIDITY = "humidity";
    private static final String PRESSURE = "pressure";
    private static final String WIND_OBJ = "wind";
    private static final String WIND_SPEED = "speed";
    private static final String WIND_DIRECTION = "deg";
    private static final String DAILY_LIST = "list";

    public static ArrayList<ForecastSingleDay> getForecastDailyList(String jsonForecastStr) throws JSONException {
        ArrayList<ForecastSingleDay> forecastList = new ArrayList<>();
        JSONObject jsonForecastDailyObj = new JSONObject(jsonForecastStr);
        JSONArray jsonForecastArray = jsonForecastDailyObj.getJSONArray(DAILY_LIST);

        // we are starting at 1 because request will return current day, which we already have.
        for (int i = 1; i < jsonForecastArray.length(); i++) {
            JSONObject mainJsonObj = jsonForecastArray.getJSONObject(i);
            ForecastSingleDay forecastToday = parseDailySingleDay(mainJsonObj);
            forecastList.add(forecastToday);
        }
        return forecastList;
    }

    public static ForecastSingleDay parseDailySingleDay(JSONObject jsonForecastObj) throws JSONException {
        ForecastSingleDay forecastToday = new ForecastSingleDay();

        // date fields
        forecastToday.setEpochDate(FieldFormatter.convertEpochToMilli(jsonForecastObj.getLong(DATE)));
        forecastToday.setDayOfWeek(FieldFormatter.epochDateToDayOfWeekName(forecastToday.getEpochDate()));
        forecastToday.setMonth(FieldFormatter.epochToMonthName(forecastToday.getEpochDate()));
        forecastToday.setDayOfMonth(FieldFormatter.epochToDayOfMonth(forecastToday.getEpochDate()));
        forecastToday.setHumidity(checkIfIntMissing(jsonForecastObj, HUMIDITY));
        forecastToday.setPressure(checkIfDoubleMissing(jsonForecastObj, PRESSURE));
        forecastToday.setWindSpeed(checkIfDoubleMissing(jsonForecastObj, WIND_SPEED));
        forecastToday.setWindDirection(FieldFormatter.windDegreesToDirection(checkIfDoubleMissing(jsonForecastObj, WIND_DIRECTION)));

        // main object fields
        JSONObject tempJsonObj = jsonForecastObj.getJSONObject(TEMP);
        forecastToday.setTemp(tempJsonObj.getDouble(DAY));
        forecastToday.setMaxTemp(tempJsonObj.getDouble(MAX));
        forecastToday.setMinTemp(tempJsonObj.getDouble(MIN));

        // weather object fields
        JSONObject weatherJsonObj = jsonForecastObj.getJSONArray(WEATHER_OBJ).getJSONObject(0);
        forecastToday.setConditionDesc(weatherJsonObj.getString(WEATHER_DESC));
        forecastToday.setIcon(weatherJsonObj.getString(WEATHER_ICON));

        return forecastToday;
    }

    public static ForecastSingleDay getForecastCurrentSingleDay(String jsonForecastStr) throws JSONException {
        JSONObject jsonForecastObj = new JSONObject(jsonForecastStr);
        return parseCurrentSingleDay(jsonForecastObj);
    }

    public static ForecastSingleDay parseCurrentSingleDay(JSONObject jsonForecastObj) throws JSONException {
        ForecastSingleDay forecastToday = new ForecastSingleDay();

        // date fields
        forecastToday.setEpochDate(FieldFormatter.convertEpochToMilli(jsonForecastObj.getLong(DATE)));
        forecastToday.setDayOfWeek(FieldFormatter.epochDateToDayOfWeekName(forecastToday.getEpochDate()));
        forecastToday.setMonth(FieldFormatter.epochToMonthName(forecastToday.getEpochDate()));
        forecastToday.setDayOfMonth(FieldFormatter.epochToDayOfMonth(forecastToday.getEpochDate()));

        // main object fields
        JSONObject mainJsonObj = jsonForecastObj.getJSONObject(MAIN_OBJ);
        forecastToday.setTemp(mainJsonObj.getDouble(TEMP));
        forecastToday.setMaxTemp(mainJsonObj.getDouble(TEMP_MAX));
        forecastToday.setMinTemp(mainJsonObj.getDouble(TEMP_MIN));
        forecastToday.setHumidity(checkIfIntMissing(mainJsonObj, HUMIDITY));
        forecastToday.setPressure(checkIfDoubleMissing(mainJsonObj, PRESSURE));

        // weather object fields
        JSONObject weatherJsonObj = jsonForecastObj.getJSONArray(WEATHER_OBJ).getJSONObject(0);
        forecastToday.setConditionDesc(weatherJsonObj.getString(WEATHER_DESC));
        forecastToday.setIcon(weatherJsonObj.getString(WEATHER_ICON));

        // wind object fields
        JSONObject windJsonObj = jsonForecastObj.getJSONObject(WIND_OBJ);
        forecastToday.setWindSpeed(checkIfDoubleMissing(windJsonObj, WIND_SPEED));
        forecastToday.setWindDirection(FieldFormatter.windDegreesToDirection(checkIfDoubleMissing(windJsonObj, WIND_DIRECTION)));

        return forecastToday;
    }

    private static int checkIfIntMissing(JSONObject currentJsonObject, String key) {
        try {
            return currentJsonObject.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static double checkIfDoubleMissing(JSONObject currentJsonObject, String key) {
        try {
            return currentJsonObject.getDouble(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

}