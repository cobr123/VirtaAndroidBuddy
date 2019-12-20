package com.virtaandroidbuddy;

import android.app.Application;

import androidx.room.Room;

import com.virtaandroidbuddy.data.Storage;
import com.virtaandroidbuddy.data.database.VirtonomicaDatabase;

public class AppDelegate extends Application {

    private Storage mStorage;

    @Override
    public void onCreate() {
        super.onCreate();

        final VirtonomicaDatabase database = Room.databaseBuilder(getApplicationContext(), VirtonomicaDatabase.class, "virtonomica_database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        mStorage = new Storage(database.getVirtonomicaDao());
    }

    public Storage getStorage() {
        return mStorage;
    }
}
