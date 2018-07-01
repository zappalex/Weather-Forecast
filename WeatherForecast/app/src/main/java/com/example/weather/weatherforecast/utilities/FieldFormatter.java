package com.example.weather.weatherforecast.utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.DataFormatException;


public class FieldFormatter {

    private static final String DATE_FORMAT_DAY_OF_WEEK_NAME = "EEEE";
    private static final String DATE_FORMAT_MONTH_NAME = "MMM";
    private static final String[] WIND_DIRECTIONS_ARR = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};

    public static Long convertEpochToMilli(Long epochDate) {
        return epochDate * 1000;
    }

    public static String epochDateToDayOfWeekName(Long epochDate) {
        SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat(DATE_FORMAT_DAY_OF_WEEK_NAME);
        Date originalDate = new Date(epochDate);
        return dayOfWeekFormat.format(originalDate);
    }

    public static int epochToDayOfMonth(Long epochDate) {
        Date originalDate = new Date(epochDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(originalDate);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static String epochToMonthName(Long epochDate) {
        SimpleDateFormat monthFormat = new SimpleDateFormat(DATE_FORMAT_MONTH_NAME);
        Date originalDate = new Date(epochDate);
        return monthFormat.format(originalDate);
    }

    public static String windDegreesToDirection(double directionInDegrees) {
        int index = (int) ((directionInDegrees / 22.5) + .5);
        return WIND_DIRECTIONS_ARR[index % 16];
    }

    public static String getFullDateStr(String month, int monthDay) {
        return "Today, " + month + " " + monthDay;
    }

    public static String getMonthAndDayStr(String month, int day) {
        return month + " " + day;
    }

    public static String getFormattedHumidity(double humidity) {
        return "Humidity: " + (int) humidity + " %";
    }

    public static String getFormattedPressure(double pressure) {
        return "Pressure: " + (int)pressure +" hPa";
    }

    public static String getFormattedWind(double speed, String direction){
        return "Wind: " + (int)speed + " km/h " + direction;
    }


    public static String getFormattedTemp(double temp) {
        return (int) temp + "°";
    }


}

