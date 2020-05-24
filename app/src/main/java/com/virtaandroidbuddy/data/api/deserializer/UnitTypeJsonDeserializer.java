package com.virtaandroidbuddy.data.api.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.virtaandroidbuddy.data.database.model.UnitType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UnitTypeJsonDeserializer implements JsonDeserializer<List<UnitType>> {
    @Override
    public List<UnitType> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

        final List<UnitType> unitTypeList = new ArrayList<>(jsonObject.size());
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            final JsonObject item = (JsonObject) entry.getValue();

            if (!"seaport".equalsIgnoreCase(item.get("kind").getAsString())) {
                final UnitType unitType = new UnitType();

                unitType.setId(item.get("id").getAsString());
                unitType.setUnitClassId(item.get("class_id").getAsString());
                unitType.setName(item.get("name").getAsString());

                unitTypeList.add(unitType);
            }
        }
        return unitTypeList;
    }
}
