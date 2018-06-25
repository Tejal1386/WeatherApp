package com.example.tejalpatel.weatherapp.Service;

import android.os.AsyncTask;

/**
 * Created by tejalpatel on 2018-06-22.
 */

public class YahooWeatherService {

    private WeatherServiceCallback callback;

    private String location;

    public YahooWeatherService (WeatherServiceCallback callback){
        this.callback = callback;
    }

    public String getLocation() {
        return location;
    }

    public  void refreshWeather(String location){
        new AsyncTask<String,Void, String>(){
            protected String doInBackground(String... strings){
                return null;
            }
            protected void onPostExecute(String s){
                super.onPostExecute(s);
            }
        }.execute(location);
    }
}
