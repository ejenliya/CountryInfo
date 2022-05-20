package com.example.test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Response {
    private JSONObject jsonObject;
    private ArrayList<HashMap<String, String>> info = new ArrayList<>();
    private JSONArray borders;

    public Response(String jsonResponse) throws JSONException {
        jsonObject = new JSONObject(jsonResponse);
        borders = jsonObject.getJSONArray("borders");
    }

    public String getCountry() throws JSONException {return jsonObject.getString("officialName");}
    public String getRegion() throws JSONException {return jsonObject.getString("region");}

    public ArrayList<HashMap<String, String>> getInfo() throws JSONException {
        for (int i = 0; i < borders.length(); i++) {
            JSONObject jo = borders.getJSONObject(i);

            String country = jo.getString("officialName");
            String region = jo.getString("region");
            String countryCode = jo.getString("countryCode");

            HashMap<String, String> item = new HashMap<>();
            item.put("country", "Country: " + country);
            item.put("code", "CountryCode: " + countryCode);
            item.put("region", "Region: " + region);

            info.add(item);
        }
        return info;
    }
}
