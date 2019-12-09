package com.ualr.loginandregister.Databases;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ualr.loginandregister.model.Page;
import com.ualr.loginandregister.model.User;

@Database(entities = {Page.class}, version = 2, exportSchema = false)
public abstract class PageDatabase extends RoomDatabase {
    public abstract PageDao getPageDao();
}