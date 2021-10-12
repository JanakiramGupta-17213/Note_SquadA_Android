package com.example.notes_squada.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category")
    List<Categories> getAllCategories();

    @Query("SELECT category FROM category")
    List<String> getCategorynames();

    @Insert
    void insertCategory(Categories category);

    @Delete
    void deleteCategory(Categories category);
}
