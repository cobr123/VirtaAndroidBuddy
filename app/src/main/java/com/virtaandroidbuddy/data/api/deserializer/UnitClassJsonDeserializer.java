package com.virtaandroidbuddy.data.api.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.virtaandroidbuddy.data.database.model.UnitClass;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UnitClassJsonDeserializer implements JsonDeserializer<List<UnitClass>> {
    @Override
    public List<UnitClass> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

        final List<UnitClass> UnitClassList = new ArrayList<>(jsonObject.size());
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            final JsonObject item = (JsonObject) entry.getValue();

            if (!"seaport".equalsIgnoreCase(item.get("kind").getAsString())) {
                final UnitClass UnitClass = new UnitClass();

                UnitClass.setId(item.get("id").getAsString());
                UnitClass.setName(item.get("name").getAsString());

                UnitClassList.add(UnitClass);
            }
        }
        return UnitClassList;
    }
}
