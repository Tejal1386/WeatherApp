package com.example.tejalpatel.weatherapp.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tejalpatel on 2018-06-22.
 */

public class Units implements JsonPopulator{

    private String temperature;

    public String getTemperature() {return temperature;}

    public void populate(JSONObject data){ temperature = data.optString("temperature") ;}

    public JSONObject toJSON(){

        JSONObject data = new JSONObject();

        try {
            data.put("temperature", temperature);
        }
        catch (JSONException e){
             e.printStackTrace();
        }
         return data;
    }
}
