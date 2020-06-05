package com.virtaandroidbuddy.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.virtaandroidbuddy.data.api.model.UnitListDataJson;
import com.virtaandroidbuddy.data.api.model.UnitListJson;
import com.virtaandroidbuddy.data.api.model.UnitSummaryJson;
import com.virtaandroidbuddy.data.database.VirtonomicaDao;
import com.virtaandroidbuddy.data.database.model.City;
import com.virtaandroidbuddy.data.database.model.Country;
import com.virtaandroidbuddy.data.database.model.Knowledge;
import com.virtaandroidbuddy.data.database.model.Region;
import com.virtaandroidbuddy.data.database.model.Session;
import com.virtaandroidbuddy.data.database.model.Unit;
import com.virtaandroidbuddy.data.database.model.UnitClass;
import com.virtaandroidbuddy.data.database.model.UnitListFilter;
import com.virtaandroidbuddy.data.database.model.UnitSummary;
import com.virtaandroidbuddy.data.database.model.UnitType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Flowable;

public class Storage {

    private static final String TAG = Storage.class.getSimpleName();

    private VirtonomicaDao mVirtonomicaDao;

    public Storage(VirtonomicaDao virtonomicaDao) {
        mVirtonomicaDao = virtonomicaDao;
    }

    public void insertUnits(String realm, String companyId, UnitListJson unitListJson) {
        final List<UnitListDataJson> data = unitListJson.getData();
        final List<Unit> unitList = new ArrayList<>(data.size());
        for (UnitListDataJson item : data) {
            final Unit unit = new Unit();
            unit.setCompanyId(companyId);
            unit.setRealm(realm);

            unit.setId(item.getId());
            unit.setName(item.getName());

            unit.setUnitClassId(item.getUnitClassId());
            unit.setUnitClassName(item.getUnitClassName());

            unit.setUnitTypeId(item.getUnitTypeId());
            unit.setUnitTypeName(item.getUnitTypeName());
            unit.setUnitTypeSymbol(item.getUnitTypeSymbol());

            unit.setCityId(item.getCityId());
            unit.setCityName(item.getCityName());

            unit.setRegionId(item.getRegionId());
            unit.setRegionName(item.getRegionName());

            unit.setCountryId(item.getCountryId());
            unit.setCountryName(item.getCountryName());
            unit.setCountrySymbol(item.getCountrySymbol());

            unit.setWorkersInHoliday(item.isWorkersInHoliday());

            unitList.add(unit);
        }
        mVirtonomicaDao.insertUnits(unitList);
    }

    public UnitListJson getUnitList(String realm, String companyId, UnitListFilter unitListFilter) {
        final List<Unit> unitList = mVirtonomicaDao.getUnitList(realm, companyId)
                .stream()
                .filter(u -> unitListFilter.getCountryId().equals("0") || unitListFilter.getCountryId().equals(u.getCountryId()))
                .filter(u -> unitListFilter.getRegionId().equals("0") || unitListFilter.getRegionId().equals(u.getRegionId()))
                .filter(u -> unitListFilter.getCityId().equals("0") || unitListFilter.getCityId().equals(u.getCityId()))
                .filter(u -> unitListFilter.getUnitClassId().equals("0") || unitListFilter.getUnitClassId().equals(u.getUnitClassId()))
                .filter(u -> unitListFilter.getUnitTypeId().equals("0") || unitListFilter.getUnitTypeId().equals(u.getUnitTypeId()))
                .collect(Collectors.toList());
        final List<UnitListDataJson> data = new ArrayList<>(unitList.size());
        for (Unit unit : unitList) {
            final UnitListDataJson dataItem = new UnitListDataJson();

            dataItem.setId(unit.getId());
            dataItem.setName(unit.getName());

            dataItem.setUnitClassId(unit.getUnitClassId());
            dataItem.setUnitClassName(unit.getUnitClassName());

            dataItem.setUnitTypeId(unit.getUnitTypeId());
            dataItem.setUnitTypeName(unit.getUnitTypeName());
            dataItem.setUnitTypeSymbol(unit.getUnitTypeSymbol());

            dataItem.setCityId(unit.getCityId());
            dataItem.setCityName(unit.getCityName());

            dataItem.setRegionId(unit.getRegionId());
            dataItem.setRegionName(unit.getRegionName());

            dataItem.setCountryId(unit.getCountryId());
            dataItem.setCountryName(unit.getCountryName());
            dataItem.setCountrySymbol(unit.getCountrySymbol());

            dataItem.setWorkersInHoliday(unit.isWorkersInHoliday());

            data.add(dataItem);
        }
        final UnitListJson unitListJson = new UnitListJson();
        unitListJson.setData(data);
        return unitListJson;
    }

    public void insertSession(Session session) {
        mVirtonomicaDao.insertSession(session);
    }

    public void deleteSession(Session session) {
        mVirtonomicaDao.deleteSession(session);
    }

    public Session getSession() {
        return mVirtonomicaDao.getSession();
    }


    public void insertUnitListFilter(UnitListFilter unitListFilter) {
        mVirtonomicaDao.insertUnitListFilter(unitListFilter);
    }

    public UnitListFilter getUnitListFilter(final Session session) {
        final UnitListFilter unitListFilter = mVirtonomicaDao.getUnitListFilter(session.getRealm(), session.getCompanyId());
        if (unitListFilter == null) {
            return new UnitListFilter(session.getRealm(), session.getCompanyId());
        } else {
            return unitListFilter;
        }
    }

    public void insertUnitSummary(String realm, String companyId, UnitSummaryJson unitSummaryJson) {
        final UnitSummary unitSummary = new UnitSummary();
        unitSummary.setId(unitSummaryJson.getId());
        unitSummary.setCompanyId(companyId);
        unitSummary.setName(unitSummaryJson.getName());
        unitSummary.setRealm(realm);
        mVirtonomicaDao.insertUnitSummary(unitSummary);
    }

    public UnitSummaryJson getUnitSummary(String realm, String unitId) {
        final UnitSummary unitSummary = mVirtonomicaDao.getUnitSummary(realm, unitId);
        final UnitSummaryJson unitSummaryJson = new UnitSummaryJson();
        unitSummaryJson.setId(unitSummary.getId());
        unitSummaryJson.setName(unitSummary.getName());
        return unitSummaryJson;
    }

    public LiveData<Knowledge> getKnowledge(String realm, String userId) {
        final LiveData<Knowledge> knowledgeLiveData = mVirtonomicaDao.getKnowledge(realm, userId);
        if (knowledgeLiveData.getValue() == null) {
            final MutableLiveData<Knowledge> emptyKnowledgeLiveData = new MutableLiveData<>();
            emptyKnowledgeLiveData.setValue(new Knowledge());
            return emptyKnowledgeLiveData;
        } else {
            return knowledgeLiveData;
        }
    }

    public void insertKnowledge(Knowledge knowledge) {
        mVirtonomicaDao.insertKnowledge(knowledge);
    }

    public interface StorageOwner {
        Storage obtainStorage();
    }

    public void insertCountryList(String realm, List<Country> countries) {
        mVirtonomicaDao.deleteAllCountries(realm);
        for (Country country : countries) {
            country.setRealm(realm);
        }
        mVirtonomicaDao.insertCountryList(countries);
    }

    public Flowable<List<Country>> getCountryList(String realm) {
        return mVirtonomicaDao.getCountryList(realm);
    }

    public void insertRegionList(String realm, List<Region> regions) {
        mVirtonomicaDao.deleteAllRegions(realm);
        for (Region region : regions) {
            region.setRealm(realm);
        }
        mVirtonomicaDao.insertRegionList(regions);
    }

    public Flowable<List<Region>> getRegionList(String realm) {
        return mVirtonomicaDao.getRegionList(realm);
    }

    public void insertCityList(String realm, List<City> cities) {
        mVirtonomicaDao.deleteAllCities(realm);
        for (City city : cities) {
            city.setRealm(realm);
        }
        mVirtonomicaDao.insertCityList(cities);
    }

    public Flowable<List<City>> getCityList(String realm) {
        return mVirtonomicaDao.getCityList(realm);
    }

    public void insertUnitClassList(String realm, List<UnitClass> unitClassList) {
        mVirtonomicaDao.deleteAllUnitClasses(realm);
        for (UnitClass unitClass : unitClassList) {
            unitClass.setRealm(realm);
        }
        mVirtonomicaDao.insertUnitClassList(unitClassList);
    }

    public Flowable<List<UnitClass>> getUnitClassList(String realm) {
        return mVirtonomicaDao.getUnitClassList(realm);
    }

    public void insertUnitTypeList(String realm, List<UnitType> unitTypeList) {
        mVirtonomicaDao.deleteAllUnitTypes(realm);
        for (UnitType unitType : unitTypeList) {
            unitType.setRealm(realm);
        }
        mVirtonomicaDao.insertUnitTypeList(unitTypeList);
    }

    public Flowable<List<UnitType>> getUnitTypeList(String realm) {
        return mVirtonomicaDao.getUnitTypeList(realm);
    }

}
