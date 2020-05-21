package com.virtaandroidbuddy.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.virtaandroidbuddy.data.database.model.City;
import com.virtaandroidbuddy.data.database.model.Company;
import com.virtaandroidbuddy.data.database.model.Country;
import com.virtaandroidbuddy.data.database.model.Knowledge;
import com.virtaandroidbuddy.data.database.model.Region;
import com.virtaandroidbuddy.data.database.model.Session;
import com.virtaandroidbuddy.data.database.model.Unit;
import com.virtaandroidbuddy.data.database.model.UnitClassKind;
import com.virtaandroidbuddy.data.database.model.UnitSummary;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface VirtonomicaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSession(Session session);

    @Query("select * from session where id = 1")
    Session getSession();

    @Delete
    void deleteSession(Session session);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUnits(List<Unit> list);

    @Query("select * from unit where realm = :realm and company_id = :companyId")
    List<Unit> getUnitList(String realm, String companyId);

    @Delete
    void deleteUnit(Unit unit);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCompany(Company company);

    @Query("select * from company where realm = :realm and id = :companyId")
    Company getCompany(String realm, String companyId);

    @Delete
    void deleteCompany(Company company);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUnitSummary(UnitSummary unitSummary);

    @Query("select * from unitsummary where realm = :realm and id = :unitId")
    UnitSummary getUnitSummary(String realm, String unitId);

    @Delete
    void deleteUnitSummary(UnitSummary unitSummary);

    @Query("select * from knowledge where realm = :realm and user_id = :userId")
    LiveData<Knowledge> getKnowledge(String realm, String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertKnowledge(Knowledge knowledge);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUnitClassKind(UnitClassKind unitClassKind);

    @Query("select * from unitclasskind where realm = :realm")
    Flowable<List<UnitClassKind>> getUnitClassKindList(String realm);

    @Query("delete from unitclasskind where realm = :realm")
    void deleteAllUnitClassKinds(String realm);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCountryList(List<Country> countries);

    @Query("select * from country where realm = :realm")
    Flowable<List<Country>> getCountryList(String realm);

    @Query("delete from country where realm = :realm")
    void deleteAllCountries(String realm);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRegionList(List<Region> regions);

    @Query("select * from region where realm = :realm")
    Flowable<List<Region>> getRegionList(String realm);

    @Query("select * from region where realm = :realm and country_id = :countryId")
    Flowable<List<Region>> getRegionListByCountry(String realm, String countryId);

    @Query("delete from region where realm = :realm")
    void deleteAllRegions(String realm);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCityList(List<City> cities);

    @Query("select * from city where realm = :realm")
    Flowable<List<City>> getCityList(String realm);

    @Query("select * from city where realm = :realm and country_id = :countryId")
    Flowable<List<City>> getCityListByCountry(String realm, String countryId);

    @Query("select * from city where realm = :realm and region_id = :regionId")
    Flowable<List<City>> getCityListByRegion(String realm, String regionId);

    @Query("delete from city where realm = :realm")
    void deleteAllCities(String realm);
}
