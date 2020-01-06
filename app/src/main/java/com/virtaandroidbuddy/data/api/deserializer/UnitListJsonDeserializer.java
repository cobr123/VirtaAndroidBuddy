package com.virtaandroidbuddy.data.api.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.virtaandroidbuddy.data.api.model.UnitListDataJson;
import com.virtaandroidbuddy.data.api.model.UnitListJson;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UnitListJsonDeserializer implements JsonDeserializer<UnitListJson> {
    @Override
    public UnitListJson deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final UnitListJson unitListJson = new UnitListJson();
        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonObject info = jsonObject.get("info").getAsJsonObject();
        final JsonObject data = jsonObject.get("data").getAsJsonObject();

        unitListJson.setCount(info.get("count").getAsLong());
        unitListJson.setPage(info.get("page").getAsLong());
        unitListJson.setPageSize(info.get("page_size").getAsLong());

        final List<UnitListDataJson> dataList = new ArrayList<>(data.size());
        for (Map.Entry<String, JsonElement> entry : data.entrySet()) {
            final JsonObject item = (JsonObject) entry.getValue();
            final UnitListDataJson dataItem = new UnitListDataJson();
            dataItem.setId(item.get("id").getAsString());
            dataItem.setName(item.get("name").getAsString());
            dataItem.setUnitClassName(item.get("unit_class_name").getAsString());
            dataList.add(dataItem);
        }
        unitListJson.setData(dataList);
        return unitListJson;
    }
}
