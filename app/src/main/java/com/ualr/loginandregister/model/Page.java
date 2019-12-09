package com.ualr.loginandregister.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Page implements Serializable, Parcelable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String category;
    private String topic;
    private String pageName;
    private String pageContents;
    private int orderViewed;
    private boolean favorite;

    private static int totalViews = 0;

    public Page(String category, String topic, String pageName, String pageContents) {
        this.category = category;
        this.topic = topic;
        this.pageName = pageName;
        this.pageContents = pageContents;
        this.orderViewed = 0;
        this.favorite = false;
    }

    public Page(Parcel in) {
        id = in.readInt();
        category = in.readString();
        topic = in.readString();
        pageName = in.readString();
        pageContents = in.readString();
        orderViewed = in.readInt();
        favorite = in.readByte() != 0;
    }

    public static final Creator<Page> CREATOR = new Creator<Page>() {
        @Override
        public Page createFromParcel(Parcel in) {
            return new Page(in);
        }

        @Override
        public Page[] newArray(int size) {
            return new Page[size];
        }
    };

    public static int getTotalViews() {
        return totalViews;
    }

    public static void setTotalViews(int totalViews) {
        Page.totalViews = totalViews;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageContents() {
        return pageContents;
    }

    public void setPageContents(String pageContents) {
        this.pageContents = pageContents;
    }

    public int getOrderViewed() {
        return orderViewed;
    }

    public void setOrderViewed(int orderViewed) {
        this.orderViewed = orderViewed;
        setTotalViews(getTotalViews() + 1);
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pageName);
    }
}