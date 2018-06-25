package com.example.tejalpatel.weatherapp.data;

import org.json.JSONObject;

/**
 * Created by tejalpatel on 2018-06-22.
 */

public interface JsonPopulator {

    void populate(JSONObject data);

    JSONObject toJSON();
}
