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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UnitListJsonDeserializer implements JsonDeserializer<UnitListJson> {
    @Override
    public UnitListJson deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final UnitListJson unitListJson = new UnitListJson();
        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonObject info = jsonObject.get("info").getAsJsonObject();

        unitListJson.setCount(info.get("count").getAsLong());
        unitListJson.setPage(info.get("page").getAsLong());
        unitListJson.setPageSize(info.get("page_size").getAsLong());

        if (jsonObject.get("data").isJsonObject()) {
            final JsonObject indicators = jsonObject.get("indicators").getAsJsonObject();
            final Map<String, Set<String>> indicatorsMap = new HashMap<>();
            for (Map.Entry<String, JsonElement> entry : indicators.entrySet()) {
                final String key = entry.getKey();
                final JsonObject kinds = (JsonObject) entry.getValue();
                final Set<String> kindSet = indicatorsMap.getOrDefault(key, new HashSet<>());
                for (Map.Entry<String, JsonElement> kindEntry : kinds.entrySet()) {
                    final JsonObject item = (JsonObject) kindEntry.getValue();
                    kindSet.add(item.get("kind").getAsString());
                }
                indicatorsMap.put(key, kindSet);
            }
            final JsonObject data = jsonObject.get("data").getAsJsonObject();
            final List<UnitListDataJson> dataList = new ArrayList<>(data.size());
            for (Map.Entry<String, JsonElement> entry : data.entrySet()) {
                final JsonObject item = (JsonObject) entry.getValue();
                final UnitListDataJson dataItem = new UnitListDataJson();

                dataItem.setId(item.get("id").getAsString());
                dataItem.setName(item.get("name").getAsString());
                dataItem.setUnitClassId(item.get("unit_class_id").getAsString());
                dataItem.setUnitClassName(item.get("unit_class_name").getAsString());
                dataItem.setUnitClassKind(item.get("unit_class_kind").getAsString());
                dataItem.setUnitTypeId(item.get("unit_type_id").getAsString());
                dataItem.setUnitTypeSymbol(item.get("unit_type_symbol").getAsString());
                if (item.get("productivity") == null) {
                    dataItem.setUnitProductivity(-1);
                } else {
                    dataItem.setUnitProductivity(Double.parseDouble(item.get("productivity").getAsString()) * 100.0);
                }

                dataItem.setCityId(item.get("city_id").getAsString());
                dataItem.setCityName(item.get("city_name").getAsString());
                dataItem.setRegionId(item.get("region_id").getAsString());
                dataItem.setRegionName(item.get("region_name").getAsString());
                dataItem.setCountryId(item.get("country_id").getAsString());

                dataItem.setCountrySymbol(item.get("country_symbol").getAsString());

                if (indicatorsMap.getOrDefault(dataItem.getId(), new HashSet<>()).contains("workers_in_holiday")) {
                    dataItem.setWorkersInHoliday(true);
                } else {
                    dataItem.setWorkersInHoliday(false);
                }

                dataList.add(dataItem);
            }
            unitListJson.setData(dataList);
        }
        return unitListJson;
    }
}
