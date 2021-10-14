package com.example.notes_squada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.notes_squada.Database.NotesDatabase;

import java.util.List;

public class NotesEntryActivity extends AppCompatActivity {

    EditText et_title,et_description;

    ImageView imv_voice,imv_image;

    Button btn_image,btn_save;

    NotesDatabase notesDatabase;

    int id;
    String title,description,category,date,audiopath,videopath,latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_entry);

        getSupportActionBar().setTitle("Enter Notes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_title = findViewById(R.id.et_title);
        et_description = findViewById(R.id.et_description);

        imv_voice = findViewById(R.id.imv_voice);
        imv_image = findViewById(R.id.imv_image);

        btn_image = findViewById(R.id.btn_image);
        btn_save = findViewById(R.id.btn_save);

        notesDatabase = Room.databaseBuilder(this,NotesDatabase.class,"notes").allowMainThreadQueries().build();
        List<Integer> idlist = notesDatabase.notesDao().getallid();

        if(idlist.isEmpty())
        {
            id = 1;
        }
        else
        {
            id = idlist.get(idlist.size() - 1) + 1;
        }

        imv_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}