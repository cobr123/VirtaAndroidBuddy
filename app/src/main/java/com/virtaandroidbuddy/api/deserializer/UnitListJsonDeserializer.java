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
        final JsonObject info = jsonObject.get("info").getAsJsonObject();

        unitListJson.setId("count = " + info.get("count").getAsString());
        unitListJson.setName("page_size = " + info.get("page_size").getAsString());

        return unitListJson;
    }
}
