package com.virtaandroidbuddy.api.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.virtaandroidbuddy.api.model.UnitSummaryJson;

import java.lang.reflect.Type;

public class UnitSummaryJsonDeserializer implements JsonDeserializer<UnitSummaryJson> {
    @Override
    public UnitSummaryJson deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final UnitSummaryJson companyJson = new UnitSummaryJson();
        final JsonObject jsonObject = json.getAsJsonObject();

        companyJson.setId(jsonObject.get("id").getAsString());
        companyJson.setName(jsonObject.get("name").getAsString());

        return companyJson;
    }
}
