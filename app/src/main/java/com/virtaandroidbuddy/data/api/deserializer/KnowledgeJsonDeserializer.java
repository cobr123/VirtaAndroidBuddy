package com.virtaandroidbuddy.data.api.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.virtaandroidbuddy.data.database.model.Knowledge;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KnowledgeJsonDeserializer implements JsonDeserializer<List<Knowledge>> {

    @Override
    public List<Knowledge> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

        final List<Knowledge> knowledgeList = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            final JsonObject item = (JsonObject) entry.getValue();

            final String kind = item.get("kind").getAsString().toLowerCase();
            final int base = Integer.parseInt(item.get("value").getAsString());
            final int bonus = Integer.parseInt(item.get("bonus").getAsString());
            final double progress = Double.parseDouble(item.get("progress").getAsString());
            final double delta = Double.parseDouble(item.get("delta").getAsString()) * 100.0;
            final double bonus_percent = Double.parseDouble(item.get("bonus_percent").getAsString());

            final Knowledge knowledge = new Knowledge();
            knowledge.setKind(kind);
            knowledge.setBase(base);
            knowledge.setBonus(bonus);
            knowledge.setProgress(progress);
            knowledge.setDelta(delta);
            knowledge.setBonusPercent(bonus_percent);

            knowledgeList.add(knowledge);
        }
        return knowledgeList;
    }
}
