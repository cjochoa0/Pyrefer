package com.ualr.loginandregister.Databases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ualr.loginandregister.model.Page;
import com.ualr.loginandregister.model.Settings;

import java.util.List;

@Dao
public interface SettingsDao {
    @Query("Select * from Settings WHERE email = :email and password = :password")
    Settings getSettings(String email, String password);

    @Insert
    void insert(Settings settings);

    @Update
    void update(Settings settings);

    @Delete
    void delete(Settings settings);
}