package com.example.notes_squada.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class Categories {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "category")
    public String category;

    public Categories(int id, String category)
    {
        this.id = id;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }
}
