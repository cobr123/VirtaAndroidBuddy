package com.virtaandroidbuddy.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.virtaandroidbuddy.database.model.Company;
import com.virtaandroidbuddy.database.model.Session;
import com.virtaandroidbuddy.database.model.Unit;

@Database(entities = {Session.class, Unit.class, Company.class}, version = 2)
public abstract class VirtonomicaDatabase extends RoomDatabase {
    public abstract VirtonomicaDao getVirtonomicaDao();
}
