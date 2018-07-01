package com.example.weather.weatherforecast.utilities;

import com.example.weather.weatherforecast.R;


public class ImageMapper {

    public static int getImageResourceId(String imageName) {
        String imageId = imageName.substring(0, 2);
        switch (imageId) {
            case "01":
                return R.mipmap.art_clear;
            case "02":
                return R.mipmap.art_light_clouds;
            case "03":
                return R.mipmap.art_clouds;
            case "04":
                return R.mipmap.art_clouds;
            case "09":
                return R.mipmap.art_light_rain;
            case "10":
                return R.mipmap.art_rain;
            case "11":
                return R.mipmap.art_storm;
            case "13":
                return R.mipmap.art_snow;
            case "15":
                return R.mipmap.art_fog;
            default:
                return R.mipmap.art_clear;
        }
    }

    public static int getIconResourceId(String iconName) {
        String iconId = iconName.substring(0, 2);
        switch (iconId) {
            case "01":
                return R.mipmap.ic_clear;
            case "02":
                return R.mipmap.ic_light_clouds;
            case "03":
                return R.mipmap.ic_cloudy;
            case "04":
                return R.mipmap.ic_cloudy;
            case "09":
                return R.mipmap.ic_light_rain;
            case "10":
                return R.mipmap.ic_rain;
            case "11":
                return R.mipmap.ic_storm;
            case "13":
                return R.mipmap.ic_snow;
            case "15":
                return R.mipmap.ic_fog;
            default:
                return R.mipmap.ic_clear;
        }
    }
}


