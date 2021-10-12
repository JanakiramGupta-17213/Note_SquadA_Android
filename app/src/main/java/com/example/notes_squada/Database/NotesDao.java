package com.example.notes_squada.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM notes")
    List<Notes> getallnotes();

    @Query("SELECT title FROM notes")
    List<String> gettitles();

    @Query("SELECT date FROM notes")
    List<Date> getdates();

    @Query("SELECT category FROM notes")
    List<String> getcategories();

    @Insert
    void InsertNotes(Notes notes);

    @Update
    void UpdateNotes(Notes note);

    @Delete
    void DeleteNotes(Notes note);
}
