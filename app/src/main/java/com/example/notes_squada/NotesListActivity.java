package com.example.notes_squada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NotesListActivity extends AppCompatActivity {

    RecyclerView rv_notes;
    FloatingActionButton fab_addnotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        String Category = getIntent().getStringExtra("Category");
        getSupportActionBar().setTitle(Category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rv_notes = findViewById(R.id.rv_notes);
        fab_addnotes = findViewById(R.id.fab_addnotes);
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