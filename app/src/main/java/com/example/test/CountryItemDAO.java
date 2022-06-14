package com.example.test;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CountryItemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertItem(CountryItem item);

    @Query("SELECT * FROM CountryItem")
    public CountryItem getItems();
}