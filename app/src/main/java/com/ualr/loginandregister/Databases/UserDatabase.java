package com.ualr.loginandregister.Databases;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ualr.loginandregister.model.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();
}