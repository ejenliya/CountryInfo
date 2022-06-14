package com.example.test;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CountryItem.class},version = 1)
public abstract class CountryItemDB extends RoomDatabase {
    public static CountryItemDB instance;
    public abstract CountryItemDAO itemDAO();
}

