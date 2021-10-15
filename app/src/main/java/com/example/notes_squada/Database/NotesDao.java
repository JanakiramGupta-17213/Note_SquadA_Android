package com.example.notes_squada.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM notes")
    List<Notes> getallnotes();

    @Query("SELECT * FROM notes WHERE id IN (:notesid)")
    Notes getnotesbyid(int notesid);

    @Query("SELECT * FROM notes WHERE category IN (:categoryname)")
    List<Notes> getallnotesbycategory(String categoryname);

    @Query("SELECT id FROM notes")
    List<Integer> getallid();

    @Query("SELECT title FROM notes")
    List<String> getalltitles();

    @Query("SELECT id FROM notes WHERE category IN (:categoryname)")
    List<Integer> getallidbycategory(String categoryname);

    @Query("SELECT title FROM notes WHERE category IN (:categoryname)")
    List<String> gettitlesbycategory(String categoryname);

    @Query("SELECT date FROM notes WHERE category IN (:categoryname)")
    List<String> getdatesbycategory(String categoryname);

    @Insert
    void InsertNotes(Notes notes);

    @Update
    void UpdateNotes(Notes note);

    @Delete
    void DeleteNotes(Notes note);
}
