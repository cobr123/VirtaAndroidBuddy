package com.virtaandroidbuddy.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.virtaandroidbuddy.database.model.Company;
import com.virtaandroidbuddy.database.model.Session;
import com.virtaandroidbuddy.database.model.Unit;
import com.virtaandroidbuddy.database.model.UnitSummary;

import java.util.List;

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
}
