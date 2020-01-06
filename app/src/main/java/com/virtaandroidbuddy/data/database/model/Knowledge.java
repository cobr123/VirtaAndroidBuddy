package com.virtaandroidbuddy.data.database.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@Entity(primaryKeys = {"realm", "user_id"})
public class Knowledge {
    @NonNull
    @ColumnInfo(name = "realm")
    private String mRealm;

    @NonNull
    @ColumnInfo(name = "user_id")
    private String mUserId;

    @ColumnInfo(name = "mining_base")
    private int mMiningBase;

    @ColumnInfo(name = "mining_bonus")
    private int mMiningBonus;

    @ColumnInfo(name = "power_base")
    private int mPowerBase;

    @ColumnInfo(name = "power_bonus")
    private int mPowerBonus;

    @ColumnInfo(name = "manufacture_base")
    private int mManufactureBase;

    @ColumnInfo(name = "manufacture_bonus")
    private int mManufactureBonus;

    @ColumnInfo(name = "farming_base")
    private int mFarmingBase;

    @ColumnInfo(name = "farming_bonus")
    private int mFarmingBonus;

    @ColumnInfo(name = "medicine_base")
    private int mMedicineBase;

    @ColumnInfo(name = "medicine_bonus")
    private int mMedicineBonus;

    @ColumnInfo(name = "fishing_base")
    private int mFishingBase;

    @ColumnInfo(name = "fishing_bonus")
    private int mFishingBonus;

    @ColumnInfo(name = "animal_base")
    private int mAnimalBase;

    @ColumnInfo(name = "animal_bonus")
    private int mAnimalBonus;

    @ColumnInfo(name = "research_base")
    private int mResearchBase;

    @ColumnInfo(name = "research_bonus")
    private int mResearchBonus;

    @ColumnInfo(name = "restaurant_base")
    private int mRestaurantBase;

    @ColumnInfo(name = "restaurant_bonus")
    private int mRestaurantBonus;

    @ColumnInfo(name = "trade_base")
    private int mTradeBase;

    @ColumnInfo(name = "trade_bonus")
    private int mTradeBonus;

    @ColumnInfo(name = "car_base")
    private int mCarBase;

    @ColumnInfo(name = "car_bonus")
    private int mCarBonus;

    @ColumnInfo(name = "service_base")
    private int mServiceBase;

    @ColumnInfo(name = "service_bonus")
    private int mServiceBonus;

    @ColumnInfo(name = "management_base")
    private int mManagementBase;

    @ColumnInfo(name = "management_bonus")
    private int mManagementBonus;

    @ColumnInfo(name = "it_base")
    private int mItBase;

    @ColumnInfo(name = "it_bonus")
    private int mItBonus;

    @ColumnInfo(name = "educational_base")
    private int mEducationalBase;

    @ColumnInfo(name = "educational_bonus")
    private int mEducationalBonus;

    @ColumnInfo(name = "advert_base")
    private int mAdvertBase;

    @ColumnInfo(name = "advert_bonus")
    private int mAdvertBonus;

    @NonNull
    public String getRealm() {
        return mRealm;
    }

    public void setRealm(@NonNull String realm) {
        mRealm = realm;
    }

    @NonNull
    public String getUserId() {
        return mUserId;
    }

    public void setUserId(@NonNull String userId) {
        mUserId = userId;
    }

    public int getMiningBase() {
        return mMiningBase;
    }

    public void setMiningBase(int miningBase) {
        mMiningBase = miningBase;
    }

    public int getMiningBonus() {
        return mMiningBonus;
    }

    public void setMiningBonus(int miningBonus) {
        mMiningBonus = miningBonus;
    }

    public int getPowerBase() {
        return mPowerBase;
    }

    public void setPowerBase(int powerBase) {
        mPowerBase = powerBase;
    }

    public int getPowerBonus() {
        return mPowerBonus;
    }

    public void setPowerBonus(int powerBonus) {
        mPowerBonus = powerBonus;
    }

    public int getManufactureBase() {
        return mManufactureBase;
    }

    public void setManufactureBase(int manufactureBase) {
        mManufactureBase = manufactureBase;
    }

    public int getManufactureBonus() {
        return mManufactureBonus;
    }

    public void setManufactureBonus(int manufactureBonus) {
        mManufactureBonus = manufactureBonus;
    }

    public int getFarmingBase() {
        return mFarmingBase;
    }

    public void setFarmingBase(int farmingBase) {
        mFarmingBase = farmingBase;
    }

    public int getFarmingBonus() {
        return mFarmingBonus;
    }

    public void setFarmingBonus(int farmingBonus) {
        mFarmingBonus = farmingBonus;
    }

    public int getMedicineBase() {
        return mMedicineBase;
    }

    public void setMedicineBase(int medicineBase) {
        mMedicineBase = medicineBase;
    }

    public int getMedicineBonus() {
        return mMedicineBonus;
    }

    public void setMedicineBonus(int medicineBonus) {
        mMedicineBonus = medicineBonus;
    }

    public int getFishingBase() {
        return mFishingBase;
    }

    public void setFishingBase(int fishingBase) {
        mFishingBase = fishingBase;
    }

    public int getFishingBonus() {
        return mFishingBonus;
    }

    public void setFishingBonus(int fishingBonus) {
        mFishingBonus = fishingBonus;
    }

    public int getAnimalBase() {
        return mAnimalBase;
    }

    public void setAnimalBase(int animalBase) {
        mAnimalBase = animalBase;
    }

    public int getAnimalBonus() {
        return mAnimalBonus;
    }

    public void setAnimalBonus(int animalBonus) {
        mAnimalBonus = animalBonus;
    }

    public int getResearchBase() {
        return mResearchBase;
    }

    public void setResearchBase(int researchBase) {
        mResearchBase = researchBase;
    }

    public int getResearchBonus() {
        return mResearchBonus;
    }

    public void setResearchBonus(int researchBonus) {
        mResearchBonus = researchBonus;
    }

    public int getRestaurantBase() {
        return mRestaurantBase;
    }

    public void setRestaurantBase(int restaurantBase) {
        mRestaurantBase = restaurantBase;
    }

    public int getRestaurantBonus() {
        return mRestaurantBonus;
    }

    public void setRestaurantBonus(int restaurantBonus) {
        mRestaurantBonus = restaurantBonus;
    }

    public int getTradeBase() {
        return mTradeBase;
    }

    public void setTradeBase(int tradeBase) {
        mTradeBase = tradeBase;
    }

    public int getTradeBonus() {
        return mTradeBonus;
    }

    public void setTradeBonus(int tradeBonus) {
        mTradeBonus = tradeBonus;
    }

    public int getCarBase() {
        return mCarBase;
    }

    public void setCarBase(int carBase) {
        mCarBase = carBase;
    }

    public int getCarBonus() {
        return mCarBonus;
    }

    public void setCarBonus(int carBonus) {
        mCarBonus = carBonus;
    }

    public int getServiceBase() {
        return mServiceBase;
    }

    public void setServiceBase(int serviceBase) {
        mServiceBase = serviceBase;
    }

    public int getServiceBonus() {
        return mServiceBonus;
    }

    public void setServiceBonus(int serviceBonus) {
        mServiceBonus = serviceBonus;
    }

    public int getManagementBase() {
        return mManagementBase;
    }

    public void setManagementBase(int managementBase) {
        mManagementBase = managementBase;
    }

    public int getManagementBonus() {
        return mManagementBonus;
    }

    public void setManagementBonus(int managementBonus) {
        mManagementBonus = managementBonus;
    }

    public int getItBase() {
        return mItBase;
    }

    public void setItBase(int itBase) {
        mItBase = itBase;
    }

    public int getItBonus() {
        return mItBonus;
    }

    public void setItBonus(int itBonus) {
        mItBonus = itBonus;
    }

    public int getEducationalBase() {
        return mEducationalBase;
    }

    public void setEducationalBase(int educationalBase) {
        mEducationalBase = educationalBase;
    }

    public int getEducationalBonus() {
        return mEducationalBonus;
    }

    public void setEducationalBonus(int educationalBonus) {
        mEducationalBonus = educationalBonus;
    }

    public int getAdvertBase() {
        return mAdvertBase;
    }

    public void setAdvertBase(int advertBase) {
        mAdvertBase = advertBase;
    }

    public int getAdvertBonus() {
        return mAdvertBonus;
    }

    public void setAdvertBonus(int advertBonus) {
        mAdvertBonus = advertBonus;
    }

    public String getManagementLvlString() {
        if (getManagementBonus() > 0) {
            return getManagementBase() + "+" + getManagementBonus();
        } else {
            return String.valueOf(getManagementBase());
        }
    }

    public String getAdvertLvlString() {
        if (getAdvertBonus() > 0) {
            return getAdvertBase() + "+" + getAdvertBonus();
        } else {
            return String.valueOf(getAdvertBase());
        }
    }

    public String getItLvlString() {
        if (getItBonus() > 0) {
            return getItBase() + "+" + getItBonus();
        } else {
            return String.valueOf(getItBase());
        }
    }

    public String getCarLvlString() {
        if (getCarBonus() > 0) {
            return getCarBase() + "+" + getCarBonus();
        } else {
            return String.valueOf(getCarBase());
        }
    }

    public String getMedicineLvlString() {
        if (getMedicineBonus() > 0) {
            return getMedicineBase() + "+" + getMedicineBonus();
        } else {
            return String.valueOf(getMedicineBase());
        }
    }

    public String getEducationalLvlString() {
        if (getEducationalBonus() > 0) {
            return getEducationalBase() + "+" + getEducationalBonus();
        } else {
            return String.valueOf(getEducationalBase());
        }
    }

    public String getRestaurantLvlString() {
        if (getRestaurantBonus() > 0) {
            return getRestaurantBase() + "+" + getRestaurantBonus();
        } else {
            return String.valueOf(getRestaurantBase());
        }
    }

    public String getServiceLvlString() {
        if (getServiceBonus() > 0) {
            return getServiceBase() + "+" + getServiceBonus();
        } else {
            return String.valueOf(getServiceBase());
        }
    }

    public String getTradeLvlString() {
        if (getTradeBonus() > 0) {
            return getTradeBase() + "+" + getTradeBonus();
        } else {
            return String.valueOf(getTradeBase());
        }
    }

    public String getMiningLvlString() {
        if (getMiningBonus() > 0) {
            return getMiningBase() + "+" + getMiningBonus();
        } else {
            return String.valueOf(getMiningBase());
        }
    }

    public String getManufactureLvlString() {
        if (getManufactureBonus() > 0) {
            return getManufactureBase() + "+" + getManufactureBonus();
        } else {
            return String.valueOf(getManufactureBase());
        }
    }

    public String getPowerLvlString() {
        if (getPowerBonus() > 0) {
            return getPowerBase() + "+" + getPowerBonus();
        } else {
            return String.valueOf(getPowerBase());
        }
    }

    public String getAnimalLvlString() {
        if (getAnimalBonus() > 0) {
            return getAnimalBase() + "+" + getAnimalBonus();
        } else {
            return String.valueOf(getAnimalBase());
        }
    }

    public String getFishingLvlString() {
        if (getFishingBonus() > 0) {
            return getFishingBase() + "+" + getFishingBonus();
        } else {
            return String.valueOf(getFishingBase());
        }
    }

    public String getFarmingLvlString() {
        if (getFarmingBonus() > 0) {
            return getFarmingBase() + "+" + getFarmingBonus();
        } else {
            return String.valueOf(getFarmingBase());
        }
    }

    public String getResearchLvlString() {
        if (getResearchBonus() > 0) {
            return getResearchBase() + "+" + getResearchBonus();
        } else {
            return String.valueOf(getResearchBase());
        }
    }

    public static Knowledge parseKnowledge(final Session session, final String html) {
        final Document doc = Jsoup.parse(html);
        final Elements mainValues = doc.select(".qual_item .mainValue");
        final Elements bonusValues = doc.select(".qual_item .bonusValue");
        final Elements imgs = doc.select(".qual_item img");

        final Knowledge knowledge = new Knowledge();
        knowledge.setRealm(session.getRealm());
        knowledge.setUserId(session.getUserId());

        for (int i = 0; i < mainValues.size(); i++) {
            final int base = Integer.parseInt(mainValues.get(i).text());
            final int bonus = Integer.parseInt(bonusValues.get(i).text().replace("+", ""));
            switch (imgs.get(i).attr("src").toLowerCase()) {
                case "/img/qualification/mining.png":
                    knowledge.setMiningBase(base);
                    knowledge.setMiningBonus(bonus);
                    break;
                case "/img/qualification/power.png":
                    knowledge.setPowerBase(base);
                    knowledge.setPowerBonus(bonus);
                    break;
                case "/img/qualification/manufacture.png":
                    knowledge.setManufactureBase(base);
                    knowledge.setManufactureBonus(bonus);
                    break;
                case "/img/qualification/farming.png":
                    knowledge.setFarmingBase(base);
                    knowledge.setFarmingBonus(bonus);
                    break;
                case "/img/qualification/medicine.png":
                    knowledge.setMedicineBase(base);
                    knowledge.setMedicineBonus(bonus);
                    break;
                case "/img/qualification/fishing.png":
                    knowledge.setFishingBase(base);
                    knowledge.setFishingBonus(bonus);
                    break;
                case "/img/qualification/animal.png":
                    knowledge.setAnimalBase(base);
                    knowledge.setAnimalBonus(bonus);
                    break;
                case "/img/qualification/research.png":
                    knowledge.setResearchBase(base);
                    knowledge.setResearchBonus(bonus);
                    break;
                case "/img/qualification/restaurant.png":
                    knowledge.setRestaurantBase(base);
                    knowledge.setRestaurantBonus(bonus);
                    break;
                case "/img/qualification/trade.png":
                    knowledge.setTradeBase(base);
                    knowledge.setTradeBonus(bonus);
                    break;
                case "/img/qualification/car.png":
                    knowledge.setCarBase(base);
                    knowledge.setCarBonus(bonus);
                    break;
                case "/img/qualification/service.png":
                    knowledge.setServiceBase(base);
                    knowledge.setServiceBonus(bonus);
                    break;
                case "/img/qualification/management.png":
                    knowledge.setManagementBase(base);
                    knowledge.setManagementBonus(bonus);
                    break;
                case "/img/qualification/it.png":
                    knowledge.setItBase(base);
                    knowledge.setItBonus(bonus);
                    break;
                case "/img/qualification/educational.png":
                    knowledge.setEducationalBase(base);
                    knowledge.setEducationalBonus(bonus);
                    break;
                case "/img/qualification/advert.png":
                    knowledge.setAdvertBase(base);
                    knowledge.setAdvertBonus(bonus);
                    break;
                default:
                    Log.e(Knowledge.class.getSimpleName(), "new qualification img: " + imgs.get(i).attr("src").toLowerCase());
                    break;
            }
        }
        return knowledge;
    }
}
