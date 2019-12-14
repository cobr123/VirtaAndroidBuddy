package com.virtaandroidbuddy.api.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.virtaandroidbuddy.api.model.UnitListJson;

import java.lang.reflect.Type;

public class UnitListJsonDeserializer implements JsonDeserializer<UnitListJson> {
    @Override
    public UnitListJson deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final UnitListJson unitListJson = new UnitListJson();
        final JsonObject jsonObject = json.getAsJsonObject();

        unitListJson.setId(jsonObject.get("id").getAsString());
        unitListJson.setName(jsonObject.get("name").getAsString());

        return unitListJson;
    }
}
