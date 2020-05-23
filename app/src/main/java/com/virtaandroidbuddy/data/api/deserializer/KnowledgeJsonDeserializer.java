package com.virtaandroidbuddy.data.api.deserializer;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.virtaandroidbuddy.data.database.model.Knowledge;

import java.lang.reflect.Type;
import java.util.Map;

public class KnowledgeJsonDeserializer implements JsonDeserializer<Knowledge> {
    private static final String TAG = KnowledgeJsonDeserializer.class.getSimpleName();
    @Override
    public Knowledge deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

        final Knowledge knowledge = new Knowledge();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            final JsonObject item = (JsonObject) entry.getValue();

            final int base = Integer.parseInt(item.get("value").getAsString());
            final int bonus = Integer.parseInt(item.get("bonus").getAsString());

            switch (item.get("kind").getAsString().toLowerCase()) {
                case "mining":
                    knowledge.setMiningBase(base);
                    knowledge.setMiningBonus(bonus);
                    break;
                case "power":
                    knowledge.setPowerBase(base);
                    knowledge.setPowerBonus(bonus);
                    break;
                case "manufacture":
                    knowledge.setManufactureBase(base);
                    knowledge.setManufactureBonus(bonus);
                    break;
                case "farming":
                    knowledge.setFarmingBase(base);
                    knowledge.setFarmingBonus(bonus);
                    break;
                case "medicine":
                    knowledge.setMedicineBase(base);
                    knowledge.setMedicineBonus(bonus);
                    break;
                case "fishing":
                    knowledge.setFishingBase(base);
                    knowledge.setFishingBonus(bonus);
                    break;
                case "animal":
                    knowledge.setAnimalBase(base);
                    knowledge.setAnimalBonus(bonus);
                    break;
                case "research":
                    knowledge.setResearchBase(base);
                    knowledge.setResearchBonus(bonus);
                    break;
                case "restaurant":
                    knowledge.setRestaurantBase(base);
                    knowledge.setRestaurantBonus(bonus);
                    break;
                case "trade":
                    knowledge.setTradeBase(base);
                    knowledge.setTradeBonus(bonus);
                    break;
                case "car":
                    knowledge.setCarBase(base);
                    knowledge.setCarBonus(bonus);
                    break;
                case "service":
                    knowledge.setServiceBase(base);
                    knowledge.setServiceBonus(bonus);
                    break;
                case "management":
                    knowledge.setManagementBase(base);
                    knowledge.setManagementBonus(bonus);
                    break;
                case "it":
                    knowledge.setItBase(base);
                    knowledge.setItBonus(bonus);
                    break;
                case "educational":
                    knowledge.setEducationalBase(base);
                    knowledge.setEducationalBonus(bonus);
                    break;
                case "advert":
                    knowledge.setAdvertBase(base);
                    knowledge.setAdvertBonus(bonus);
                    break;
                default:
                    Log.e(TAG, "new qualification kind: " + item.get("kind").getAsString().toLowerCase());
                    break;
            }
        }
        return knowledge;
    }
}
