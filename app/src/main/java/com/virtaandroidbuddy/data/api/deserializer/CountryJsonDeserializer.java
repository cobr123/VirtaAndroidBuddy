package com.virtaandroidbuddy.data.api.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import com.virtaandroidbuddy.data.database.model.Country;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CountryJsonDeserializer implements JsonDeserializer<List<Country>> {
    @Override
    public List<Country> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

        final List<Country> countryList = new ArrayList<>(jsonObject.size());
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            final JsonObject item = (JsonObject) entry.getValue();
            final Country country = new Country();

            country.setId(item.get("id").getAsString());
            country.setName(item.get("name").getAsString());

            countryList.add(country);
        }
        return countryList;
    }
}
