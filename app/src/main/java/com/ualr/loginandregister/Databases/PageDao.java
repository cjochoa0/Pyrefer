package com.ualr.loginandregister.Databases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ualr.loginandregister.model.Page;
import com.ualr.loginandregister.model.User;

import java.util.List;

@Dao
public interface PageDao {

    @Query("Select * from Page where category = :category and topic = :topic and pageName = :pageName")
    Page getOne(String category, String topic, String pageName);

    @Query("Select * From Page")
    List<Page> getAll();

    @Query("Select topic FROM Page WHERE category = :category GROUP BY topic ORDER BY id")
    List<String> getTopics(String category);

    @Query("SELECT * FROM Page where category = 'Basic'")
    List<Page> getBasic();

    @Query("SELECT * FROM Page where category = 'Programming'")
    List<Page> getProgramming();

    @Query("SELECT * FROM Page where category = 'Test'")
    List<Page> getTest();

    @Query("SELECT pageContents FROM Page WHERE category = 'TestAnswer' and pageName = :pageName")
    String getAnswer(String pageName);

    @Query("SELECT * FROM Page where favorite = 1")
    List<Page> getFavorites();

    @Query("SELECT * FROM Page WHERE orderViewed > 0 ORDER BY orderViewed DESC LIMIT 5")
    List<Page> getRecents();


    @Insert
    void insert(Page page);

    @Update
    void update(Page page);

    @Delete
    void delete(Page page);
}