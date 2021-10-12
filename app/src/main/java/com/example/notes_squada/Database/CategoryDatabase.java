package com.example.notes_squada.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {Categories.class},version = 1)
public abstract class CategoryDatabase extends RoomDatabase {
    public abstract CategoryDao categoryDao();
}
