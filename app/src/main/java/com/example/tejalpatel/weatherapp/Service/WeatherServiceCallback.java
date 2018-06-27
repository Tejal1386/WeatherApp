package com.example.tejalpatel.weatherapp.Service;

import com.example.tejalpatel.weatherapp.data.Channel;

/**
 * Created by tejalpatel on 2018-06-22.
 */

public interface WeatherServiceCallback {

    void serviceSuccess (Channel channel);

    void  serviceFailure(Exception exception);
}
