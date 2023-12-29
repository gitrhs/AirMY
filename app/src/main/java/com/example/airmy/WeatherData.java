package com.example.airmy;

public class WeatherData {
    private String location;
    private String day;
    private String weather;
    private String temp;
    private String windspeed;
    private String humidity;
    private String precip;
    private String feelsLike;

    public WeatherData(String location, String day, String weather, String temp, String windspeed, String humidity, String precip, String feelsLike) {
        this.location = location;
        this.day = day;
        this.weather = weather;
        this.temp = temp;
        this.windspeed = windspeed;
        this.humidity = humidity;
        this.precip = precip;
        this.feelsLike = feelsLike;
    }

    // Add getters and setters as needed
    public String getLocation() {
        return location;
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

    public String getWindspeed() {
        return windspeed;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPrecip() {
        return precip;
    }

    public String getFeelsLike() {
        return feelsLike;
    }
}
