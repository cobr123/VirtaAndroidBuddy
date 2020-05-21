package com.virtaandroidbuddy.data.api.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.virtaandroidbuddy.data.database.model.Region;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegionJsonDeserializer implements JsonDeserializer<List<Region>> {
    @Override
    public List<Region> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

        final List<Region> regionList = new ArrayList<>(jsonObject.size());
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            final JsonObject item = (JsonObject) entry.getValue();
            final Region region = new Region();

            region.setId(item.get("id").getAsString());
            region.setCountryId(item.get("country_id").getAsString());
            region.setName(item.get("name").getAsString());

            regionList.add(region);
        }
        return regionList;
    }
}
