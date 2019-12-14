package com.virtaandroidbuddy;

import android.app.Application;

import androidx.room.Room;

import com.virtaandroidbuddy.database.VirtonomicaDatabase;

public class AppDelegate extends Application {

    private VirtonomicaDatabase mVirtonomicaDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        mVirtonomicaDatabase = Room.databaseBuilder(getApplicationContext(), VirtonomicaDatabase.class, "virtonomica_database")
                .allowMainThreadQueries()
                .build();
    }

    public VirtonomicaDatabase getVirtonomicaDatabase() {
        return mVirtonomicaDatabase;
    }
}
