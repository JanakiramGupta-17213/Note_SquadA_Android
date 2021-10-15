package com.example.notes_squada.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "notes")
public class Notes {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name ="date")
    public String date;

    @ColumnInfo(name = "latitude")
    public String latitude;

    @ColumnInfo(name = "longitude")
    public String longitude;

    @ColumnInfo(name = "audiopath")
    public String audiopath;

    @ColumnInfo(name = "imagepath")
    public String imagepath;

    public Notes(int id, String title, String description, String category, String date, String latitude,
                 String longitude, String audiopath, String imagepath)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.audiopath = audiopath;
        this.imagepath = imagepath;
    }

    public int getnotesId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAudiopath() {
        return audiopath;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
