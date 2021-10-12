package com.example.notes_squada.Database;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category")
    List<String> getAllCategories();
}
