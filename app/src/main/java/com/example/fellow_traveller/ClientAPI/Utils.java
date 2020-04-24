package com.example.fellow_traveller.ClientAPI;

import com.google.gson.JsonObject;

public class Utils {
    static JsonObject buildJSON(String[] keys, String... values) {
        JsonObject json = new JsonObject();
        for (int i = 0; i < keys.length; i++) {
            json.addProperty(keys[i], values[i]);
        }
        return json;
    }
}
