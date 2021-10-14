package com.example.notes_squada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.notes_squada.Adapters.NotesListAdapter;
import com.example.notes_squada.Database.NotesDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class NotesListActivity extends AppCompatActivity {

    RecyclerView rv_notes;
    FloatingActionButton fab_addnotes;

    NotesDatabase notesDatabase;

    List<String> notes_title = new ArrayList<String>();
    List<String> notes_dates = new ArrayList<String>();

    NotesListAdapter notesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        String Category = getIntent().getStringExtra("Category");
        getSupportActionBar().setTitle(Category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rv_notes = findViewById(R.id.rv_notes);
        fab_addnotes = findViewById(R.id.fab_addnotes);

        notesDatabase = Room.databaseBuilder(this,NotesDatabase.class,"notes").allowMainThreadQueries().build();
        notes_title = notesDatabase.notesDao().gettitles();
        notes_dates = notesDatabase.notesDao().getdates();

        notesListAdapter = new NotesListAdapter(notes_title,notes_dates);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        rv_notes.setLayoutManager(layoutManager);
        rv_notes.setAdapter(notesListAdapter);

        fab_addnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesListActivity.this,NotesEntryActivity.class);
                startActivity(intent);
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