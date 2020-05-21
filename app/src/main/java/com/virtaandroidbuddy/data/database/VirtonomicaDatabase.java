package com.virtaandroidbuddy.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.virtaandroidbuddy.data.database.model.City;
import com.virtaandroidbuddy.data.database.model.Company;
import com.virtaandroidbuddy.data.database.model.Country;
import com.virtaandroidbuddy.data.database.model.Knowledge;
import com.virtaandroidbuddy.data.database.model.Region;
import com.virtaandroidbuddy.data.database.model.Session;
import com.virtaandroidbuddy.data.database.model.Unit;
import com.virtaandroidbuddy.data.database.model.UnitClassKind;
import com.virtaandroidbuddy.data.database.model.UnitSummary;

@Database(
        entities = {
                Session.class,
                Unit.class,
                Company.class,
                UnitSummary.class,
                Knowledge.class,
                Country.class,
                Region.class,
                City.class,
                UnitClassKind.class},
        version = 1)
public abstract class VirtonomicaDatabase extends RoomDatabase {
    public abstract VirtonomicaDao getVirtonomicaDao();
}
