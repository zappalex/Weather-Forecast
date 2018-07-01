package com.example.weather.weatherforecast.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class NetworkCalls {

    public static final String URL_CURRENT_WEATHER = "https://api.openweathermap.org/data/2.5/weather?id=4180439&units=imperial&appid=af52ca12a8a7a4ea06c2b16fd61448df";
    public static final String URL_DAILY_WEATHER = "https://api.openweathermap.org/data/2.5/forecast/daily?id=4180439&units=imperial&cnt=6&appid=af52ca12a8a7a4ea06c2b16fd61448df";

    public static URL convertStringToUrl(String url){
        URL returUrl = null;
        try {
            returUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return returUrl;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static boolean isDeviceOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}
