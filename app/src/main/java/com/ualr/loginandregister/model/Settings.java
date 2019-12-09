package com.ualr.loginandregister.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Settings implements Serializable{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String email;
    private String password;
    private boolean dark;
    private int size;
    private int defPage;

    private static int totalViews = 0;

    public Settings(String email, String password) {
        this.email = email;
        this.password = password;
        this.dark = false;
        this.size = 2;
        this.defPage = 2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDark() {
        return dark;
    }

    public void setDark(boolean dark) {
        this.dark = dark;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDefPage() {
        return defPage;
    }

    public void setDefPage(int defPage) {
        this.defPage = defPage;
    }

    public static int getTotalViews() {
        return totalViews;
    }

    public static void setTotalViews(int totalViews) {
        Settings.totalViews = totalViews;
    }
}