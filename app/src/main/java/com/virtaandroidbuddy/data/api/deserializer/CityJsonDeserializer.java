package com.virtaandroidbuddy.data.api.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.virtaandroidbuddy.data.database.model.City;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CityJsonDeserializer implements JsonDeserializer<List<City>> {
    @Override
    public List<City> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

        final List<City> cityList = new ArrayList<>(jsonObject.size());
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            final JsonObject item = (JsonObject) entry.getValue();
            final City city = new City();

            city.setId(item.get("city_id").getAsString());
            city.setRegionId(item.get("region_id").getAsString());
            city.setCountryId(item.get("country_id").getAsString());
            city.setName(item.get("city_name").getAsString());

            cityList.add(city);
        }
        return cityList;
    }
}
