package com.example.tejalpatel.weatherapp.data;

import android.content.ClipData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tejalpatel on 2018-06-22.
 */

public class Channel implements JsonPopulator{

    private Units units;
    private Items item;
    private String location;

    public Units getUnits() {
        return units;
    }

    public Items getItem() {
        return item;
    }

    public String getLocation() {
        return location;
    }

    public void populate(JSONObject data){
        units = new Units();
        units.populate(data.optJSONObject("units"));

        item = new Items();
        item.populate(data.optJSONObject("item"));

        JSONObject locationData = data.optJSONObject("location");

        String region = locationData.optString("region");
        String country = locationData.optString("country");

        location = String.format("%s, %s", locationData.optString("city"), (region.length() != 0 ? region : country));

    }

    public JSONObject toJSON(){
        JSONObject data = new JSONObject();
        try {
            data.put("units", units.toJSON());
            data.put("item", item.toJSON());
            data.put("requestLocation", location);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
