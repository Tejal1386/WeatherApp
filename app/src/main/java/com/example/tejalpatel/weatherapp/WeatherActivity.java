package com.example.tejalpatel.weatherapp;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tejalpatel.weatherapp.Service.WeatherServiceCallback;
import com.example.tejalpatel.weatherapp.Service.YahooWeatherService;
import com.example.tejalpatel.weatherapp.data.Channel;
import com.example.tejalpatel.weatherapp.data.Items;

public class WeatherActivity extends AppCompatActivity implements WeatherServiceCallback {

    private ImageView weatherIconImageView;
    private TextView tempTextView;
    private TextView conditionTextView;
    private TextView locationTextView;

    private YahooWeatherService service;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        weatherIconImageView = (ImageView) findViewById(R.id.imageViewWeatherIcon);
        tempTextView = (TextView) findViewById(R.id.textViewTemperature);
        conditionTextView = (TextView) findViewById(R.id.textViewCondition);
        locationTextView = (TextView) findViewById(R.id.textViewLocation);

        service = new YahooWeatherService(this);
       // dialog.setMessage("Loading...");
       // dialog.show();
        service.refreshWeather("Sydney, Australia");

    }


    @Override
    public void serviceSuccess(Channel channel) {
       // dialog.hide();

        Items items = channel.getItem();
        int resourceID = getResources().getIdentifier("drawable/icon_"+ channel.getItem().getCondition().getCode(),null,getPackageName());
        Drawable weatherIconDrawable = getResources().getDrawable(resourceID);

        weatherIconImageView.setImageDrawable(weatherIconDrawable);
        tempTextView.setText(items.getCondition().getTemperature()+" "+ channel.getUnits().getTemperature());
        conditionTextView.setText(items.getCondition().getDescription());
        locationTextView.setText(service.getLocation());
    }

    @Override
    public void serviceFailure(Exception exception){
       // dialog.hide();
        Toast.makeText(this,exception.getMessage(),Toast.LENGTH_LONG).show();
    }
}
