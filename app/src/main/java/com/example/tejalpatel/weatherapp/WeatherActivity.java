package com.example.tejalpatel.weatherapp;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tejalpatel.weatherapp.Fragment.WeatherConditionFragment;
import com.example.tejalpatel.weatherapp.Service.WeatherServiceCallback;
import com.example.tejalpatel.weatherapp.Service.YahooWeatherService;
import com.example.tejalpatel.weatherapp.data.Channel;
import com.example.tejalpatel.weatherapp.data.Condition;
import com.example.tejalpatel.weatherapp.data.Items;
import com.example.tejalpatel.weatherapp.data.Units;

public class WeatherActivity extends AppCompatActivity implements WeatherServiceCallback {

    private ImageView weatherIconImageView;
    private TextView tempTextView;
    private TextView conditionTextView;
    private TextView locationTextView;
    private Spinner spnLocation;
    private YahooWeatherService service;
    private ProgressDialog dialog;

    String location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        weatherIconImageView = (ImageView) findViewById(R.id.imageViewWeatherIcon);
        tempTextView = (TextView) findViewById(R.id.textViewTemperature);
        conditionTextView = (TextView) findViewById(R.id.textViewCondition);
        locationTextView = (TextView) findViewById(R.id.textViewLocation);
        spnLocation = (Spinner) findViewById(R.id.spnLocation);

        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.loading));
        dialog.setCancelable(false);
        dialog.show();


        spnLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                location = spnLocation.getSelectedItem().toString();

                service = new YahooWeatherService(WeatherActivity.this);
                 dialog.setMessage("Loading...");
                 dialog.show();
                service.refreshWeather(location);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                service = new YahooWeatherService(WeatherActivity.this);
                service.refreshWeather("Toronto, Canada");
            }
        });

        service = new YahooWeatherService(this);
       // dialog.setMessage("Loading...");
       // dialog.show();
        service.refreshWeather("Toronto, Canada");

    }


    @Override
    public void serviceSuccess(Channel channel) {
        dialog.hide();

        Items items = channel.getItem();
        int resourceID = getResources().getIdentifier("drawable/icon_"+ channel.getItem().getCondition().getCode(),null,getPackageName());
        Drawable weatherIconDrawable = getResources().getDrawable(resourceID);

        weatherIconImageView.setImageDrawable(weatherIconDrawable);
        tempTextView.setText(items.getCondition().getTemperature()+" "+ channel.getUnits().getTemperature());
        conditionTextView.setText(items.getCondition().getDescription());
        locationTextView.setText(service.getLocation());


        Condition condition = channel.getItem().getCondition();
        Units units = channel.getUnits();
        Condition[] forecast = channel.getItem().getForecast();

        int weatherIconImageResource = getResources().getIdentifier("icon_" + condition.getCode(), "drawable", getPackageName());


        for (int day = 0; day < forecast.length; day++) {
            if (day >= 6) {
                break;
            }

            Condition currentCondition = forecast[day];

            int viewId = getResources().getIdentifier("forecast_" + day, "id", getPackageName());
            WeatherConditionFragment fragment = (WeatherConditionFragment) getSupportFragmentManager().findFragmentById(viewId);

            if (fragment != null) {
                fragment.loadForecast(currentCondition, channel.getUnits());
            }
        }


    }

    @Override
    public void serviceFailure(Exception exception){
       // dialog.hide();
        Toast.makeText(this,exception.getMessage(),Toast.LENGTH_LONG).show();
    }
}
