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

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name ="date")
    public Date date;

    @ColumnInfo(name = "latitude")
    public String latitude;

    @ColumnInfo(name = "longitude")
    public String longitude;

    @ColumnInfo(name = "audiopath")
    public String audiopath;

    @ColumnInfo(name = "imagepath")
    public String imagepath;

    public Notes(int id, String title, String catergory, Date date, String latitude,
                 String longitude, String audipath, String imagepath)
    {
        this.id = id;
        this.title = title;
        this.category = catergory;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.audiopath = audipath;
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

    public Date getDate() {
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
}
