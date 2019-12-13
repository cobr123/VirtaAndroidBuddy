package com.virtaandroidbuddy.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.virtaandroidbuddy.database.model.Session;
import com.virtaandroidbuddy.database.model.Unit;

@Database(entities = {Session.class, Unit.class}, version = 1)
public abstract class VirtonomicaDatabase extends RoomDatabase {
    abstract VirtonomicaDao getVirtonomicaDao();
}
