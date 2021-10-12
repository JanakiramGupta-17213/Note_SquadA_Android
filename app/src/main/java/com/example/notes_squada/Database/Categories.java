package com.example.notes_squada.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "category")
public class Categories {

    @ColumnInfo(name = "category")
    public String category;

    public Categories(String category)
    {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
