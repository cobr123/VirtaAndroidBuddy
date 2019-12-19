package com.virtaandroidbuddy.data.api.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.virtaandroidbuddy.data.api.model.CompanyJson;

import java.lang.reflect.Type;

public class CompanyJsonDeserializer implements JsonDeserializer<CompanyJson> {
    @Override
    public CompanyJson deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final CompanyJson companyJson = new CompanyJson();
        final JsonObject jsonObject = json.getAsJsonObject();

        companyJson.setId(jsonObject.get("id").getAsString());
        companyJson.setName(jsonObject.get("name").getAsString());
        companyJson.setPresidentUserId(jsonObject.get("president_user_id").getAsString());

        return companyJson;
    }
}
