package com.example.test;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CountryItem {
    @PrimaryKey
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "country")
    public String country;

    @ColumnInfo(name = "region")
    public String region;

    @ColumnInfo(name = "code")
    public String code;
}
