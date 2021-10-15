package com.example.notes_squada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.notes_squada.Adapters.NotesListAdapter;
import com.example.notes_squada.Database.Notes;
import com.example.notes_squada.Database.NotesDatabase;
import com.example.notes_squada.Models.NotesData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NotesListActivity extends AppCompatActivity {

    ConstraintLayout cl_parentnotes;
    SearchView sv_notes;
    RecyclerView rv_notes;
    FloatingActionButton fab_addnotes;

    NotesDatabase notesDatabase;

    List<Notes> allnotes = new ArrayList<Notes>();
    List<String> notes_title = new ArrayList<String>();
    List<String> notes_dates = new ArrayList<String>();
    List<Integer> notes_id = new ArrayList<Integer>();

    List<NotesData> notesDataList = new ArrayList<NotesData>();

    NotesListAdapter notesListAdapter;

    int titlesort = 2;
    int datesort = 2;

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        String Category = getIntent().getStringExtra("Category");
        getSupportActionBar().setTitle(Category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cl_parentnotes = findViewById(R.id.cl_parentnotes);
        sv_notes = findViewById(R.id.sv_notes);
        rv_notes = findViewById(R.id.rv_notes);
        fab_addnotes = findViewById(R.id.fab_addnotes);

        notesDatabase = Room.databaseBuilder(getApplicationContext(),NotesDatabase.class,"notes").allowMainThreadQueries().build();

        allnotes = notesDatabase.notesDao().getallnotesbycategory(Category);

        notes_title = notesDatabase.notesDao().gettitlesbycategory(Category);
        notes_dates = notesDatabase.notesDao().getdatesbycategory(Category);
        notes_id = notesDatabase.notesDao().getallidbycategory(Category);

        loadData();

        notesListAdapter = new NotesListAdapter(notes_title,notes_dates,notes_id);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        rv_notes.setLayoutManager(layoutManager);
        rv_notes.setAdapter(notesListAdapter);

        ItemTouchHelper.SimpleCallback itsc = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int index = viewHolder.getAdapterPosition();
                String name = notes_title.get(index);
                Notes note = allnotes.get(index);

                AlertDialog.Builder alert = new AlertDialog.Builder(NotesListActivity.this);
                alert.setTitle("Delete Notes");
                alert.setMessage("Click on Yes to delete Note : "+name);
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        notesDatabase.notesDao().DeleteNotes(note);
                        notesDataList.remove(index);
                        dataReload();
                        notesListAdapter.notifyItemRemoved(index);
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        notesListAdapter.notifyDataSetChanged();
                        dialogInterface.dismiss();
                    }
                });
                alert.create().show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itsc);
        itemTouchHelper.attachToRecyclerView(rv_notes);

        fab_addnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesListActivity.this,NotesEntryActivity.class);
                intent.putExtra("category",Category);
                startActivity(intent);
            }
        });


        sv_notes.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                notesListAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                notesListAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    public void loadData()
    {
        notesDataList.clear();
        for(int i =0; i < notes_id.size(); i++)
        {
            NotesData notesData = new NotesData(notes_id.get(i),notes_title.get(i),notes_dates.get(i));
            notesDataList.add(notesData);
            System.out.println(notesData.getDate());
        }
    }

    public void dataReload()
    {
        notes_id.clear();
        notes_title.clear();
        notes_dates.clear();
        for(int i =0; i < notesDataList.size(); i++)
        {
            notes_id.add(notesDataList.get(i).getId());
            notes_title.add(notesDataList.get(i).getTitle());
            notes_dates.add(notesDataList.get(i).getDate());
        }
    }

    public void sortTitle()
    {
        if(titlesort == 2)
        {
            Collections.sort(notesDataList, new Comparator<NotesData>() {
                @Override
                public int compare(NotesData notesData, NotesData t1) {
                    return notesData.getTitle().compareTo(t1.getTitle());
                }
            });
            titlesort = 1;
        }
        else if(titlesort == 1)
        {
            Collections.sort(notesDataList, new Comparator<NotesData>() {
                @Override
                public int compare(NotesData notesData, NotesData t1) {
                    return notesData.getTitle().compareTo(t1.getTitle());
                }
            });
            Collections.reverse(notesDataList);
            titlesort = 2;
        }
        dataReload();
        notesListAdapter.notifyDataSetChanged();
    }

    public void sortDate()
    {
        if(datesort == 2)
        {
            Collections.sort(notesDataList, new Comparator<NotesData>() {
                @Override
                public int compare(NotesData notesData, NotesData t1) {
                    return notesData.getDate().compareTo(t1.getDate());
                }
            });
            datesort = 1;
        }
        else if(datesort == 1)
        {
            Collections.sort(notesDataList, new Comparator<NotesData>() {
                @Override
                public int compare(NotesData notesData, NotesData t1) {
                    return notesData.getDate().compareTo(t1.getDate());
                }
            });
            Collections.reverse(notesDataList);
            datesort = 2;
        }
        dataReload();
        notesListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            case R.id.sort_title:
                sortTitle();
                break;
            case R.id.sort_date:
                sortDate();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
