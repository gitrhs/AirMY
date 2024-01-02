package com.example.airmy;

public class WeatherData {
    private String dailyChanceOfRain;
    private String day;
    private String weather;
    private String temp;
    private String maxTemp;
    private String humidity;
    private String minTemp;
    private String uv;

    public WeatherData(String dailyChanceOfRain, String day, String weather, String temp, String maxTemp, String humidity, String minTemp, String uv) {
        this.dailyChanceOfRain = dailyChanceOfRain;
        this.day = day;
        this.weather = weather;
        this.temp = temp;
        this.maxTemp = maxTemp;
        this.humidity = humidity;
        this.minTemp = minTemp;
        this.uv = uv;
    }

    // Add getters and setters as needed
    public String getdailyChanceOfRain() {
        return dailyChanceOfRain;
    }

    public String getDay() {
        return day;
    }

    public String getWeather() {
        return weather;
    }

    public String getTemp() {
        return temp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public String getUV() {
        return uv;
    }
}