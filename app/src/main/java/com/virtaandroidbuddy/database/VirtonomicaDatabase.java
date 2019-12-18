package com.virtaandroidbuddy.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.virtaandroidbuddy.database.model.Company;
import com.virtaandroidbuddy.database.model.Session;
import com.virtaandroidbuddy.database.model.Unit;
import com.virtaandroidbuddy.database.model.UnitSummary;

@Database(entities = {Session.class, Unit.class, Company.class, UnitSummary.class}, version = 3)
public abstract class VirtonomicaDatabase extends RoomDatabase {
    public abstract VirtonomicaDao getVirtonomicaDao();
}
