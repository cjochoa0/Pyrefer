package com.ualr.loginandregister.Databases;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ualr.loginandregister.model.Page;
import com.ualr.loginandregister.model.Settings;

@Database(entities = {Settings.class}, version = 1, exportSchema = false)
public abstract class SettingsDatabase extends RoomDatabase {
    public abstract SettingsDao getSettingsDao();
}