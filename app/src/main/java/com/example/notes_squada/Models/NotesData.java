package com.example.notes_squada.Models;

public class NotesData {
    int id;
    String title,date;

    public NotesData(int id, String title, String date)
    {
        this.id = id;
        this.title = title;
        this.date = date;
    }
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDate() {
        return date;
    }
}
