package com.example.notes_squada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class NotesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        String Category = getIntent().getStringExtra("Category");
        getSupportActionBar().setTitle(Category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}