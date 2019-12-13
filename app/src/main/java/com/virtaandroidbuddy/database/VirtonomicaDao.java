package com.virtaandroidbuddy.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.virtaandroidbuddy.database.model.Session;
import com.virtaandroidbuddy.database.model.Unit;

import java.util.List;

@Dao
public interface VirtonomicaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSession(Session session);

    @Query("select * from session")
    Session getSession();

    @Delete
    void deleteSession(Session session);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUnits(List<Unit> list);

    @Query("select * from unit where realm = :realm and company_id = :companyId")
    List<Unit> getUnitList(String realm, String companyId);

    @Delete
    void deleteUnit(Unit unit);
}
