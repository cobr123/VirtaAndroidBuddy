package com.virtaandroidbuddy.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.virtaandroidbuddy.data.database.model.Company;
import com.virtaandroidbuddy.data.database.model.Session;
import com.virtaandroidbuddy.data.database.model.Unit;
import com.virtaandroidbuddy.data.database.model.UnitSummary;

@Database(entities = {Session.class, Unit.class, Company.class, UnitSummary.class}, version = 3)
public abstract class VirtonomicaDatabase extends RoomDatabase {
    public abstract VirtonomicaDao getVirtonomicaDao();
}
