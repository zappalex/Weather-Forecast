package com.example.weather.weatherforecast.models;

import android.os.Parcel;
import android.os.Parcelable;


public class ForecastSingleDay implements Parcelable {

    private String dayOfWeek;
    private String month;
    private int dayOfMonth;
    private Long epochDate;
    private double temp;
    private double maxTemp;
    private double minTemp;
    private String conditionDesc;
    private String icon;
    private int humidity;
    private double pressure;
    private double windSpeed;
    private String windDirection;

    public ForecastSingleDay(){

    }

    public ForecastSingleDay(Parcel in){
        dayOfWeek = in.readString();
        month = in.readString();
        dayOfMonth = in.readInt();
        epochDate = in.readLong();
        temp = in.readDouble();
        maxTemp = in.readDouble();
        minTemp = in.readDouble();
        conditionDesc = in.readString();
        icon = in.readString();
        humidity = in.readInt();
        pressure = in.readDouble();
        windSpeed = in.readDouble();
        windDirection = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dayOfWeek);
        dest.writeString(month);
        dest.writeInt(dayOfMonth);
        dest.writeLong(epochDate);
        dest.writeDouble(temp);
        dest.writeDouble(maxTemp);
        dest.writeDouble(minTemp);
        dest.writeString(conditionDesc);
        dest.writeString(icon);
        dest.writeInt(humidity);
        dest.writeDouble(pressure);
        dest.writeDouble(windSpeed);
        dest.writeString(windDirection);
    }

    public static final Parcelable.Creator<ForecastSingleDay> CREATOR= new Parcelable.Creator<ForecastSingleDay>(){
        @Override
        public ForecastSingleDay createFromParcel(Parcel source) {
            return new ForecastSingleDay(source);
        }

        @Override
        public ForecastSingleDay[] newArray(int size) {
            return new ForecastSingleDay[0];
        }
    };

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getMonth() { return month; }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public Long getEpochDate() {
        return epochDate;
    }

    public void setEpochDate(Long epochDate) {
        this.epochDate = epochDate;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    public void setConditionDesc(String conditionDesc) {
        this.conditionDesc = conditionDesc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }
}
