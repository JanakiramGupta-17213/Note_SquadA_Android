package com.example.notes_squada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.notes_squada.Database.Categories;
import com.example.notes_squada.Database.CategoryDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CategoriesActivity extends AppCompatActivity {

    TextView tv_cattext,tv_catsave,tv_catcancel;
    RecyclerView rv_cat;
    EditText et_catname;
    FloatingActionButton fab_addcat;
    List<Categories> catnames;

    LinearLayout ll_createcat;

    CategoryDatabase categoryDatabase;

    List<String> categories;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        getSupportActionBar().setTitle("Categories");

        categoryDatabase = Room.databaseBuilder(getApplicationContext(), CategoryDatabase.class,"category").allowMainThreadQueries().build();

        catnames = categoryDatabase.categoryDao().getAllCategories();

        tv_cattext = findViewById(R.id.tv_cattext);

        tv_catsave = findViewById(R.id.tv_catsave);

        tv_catcancel = findViewById(R.id.tv_catcancel);

        rv_cat = findViewById(R.id.rv_cat);

        et_catname = findViewById(R.id.et_catname);

        fab_addcat = findViewById(R.id.fab_addcat);

        ll_createcat = findViewById(R.id.ll_createcat);



        if(catnames.isEmpty())
        {
            tv_cattext.setVisibility(View.VISIBLE);
            id = 1;
        }
        else
        {
            tv_cattext.setVisibility(View.GONE);
            id = catnames.get(catnames.size() - 1).getId() + 1;
        }

        categories = categoryDatabase.categoryDao().getCategorynames();


        //Setting Layout Manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv_cat.setLayoutManager(layoutManager);

        //Setting Adapter
        CatListAdapter adapter = new CatListAdapter(categories);
        rv_cat.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itsc = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CategoriesActivity.this);
                alert.setTitle("Delete Category");
                alert.setMessage("Do you want to delete category: "+categories.get(viewHolder.getAdapterPosition()));
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        categoryDatabase.categoryDao().deleteCategory(catnames.get(viewHolder.getAdapterPosition()));
                        catnames.remove(viewHolder.getAdapterPosition());
                        categories.remove(viewHolder.getAdapterPosition());
                        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                        catnames = categoryDatabase.categoryDao().getAllCategories();
                        categories = categoryDatabase.categoryDao().getCategorynames();
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        adapter.notifyDataSetChanged();
                    }
                });
                alert.create().show();

            }
        };
        ItemTouchHelper ith = new ItemTouchHelper(itsc);
        ith.attachToRecyclerView(rv_cat);

        fab_addcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_cattext.setVisibility(View.GONE);
                rv_cat.setVisibility(View.GONE);
                fab_addcat.setVisibility(View.GONE);

                ll_createcat.setVisibility(View.VISIBLE);
            }
        });

        tv_catsave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                List<String> uniqcat = new ArrayList<>();
                List<String> cat = categoryDatabase.categoryDao().getCategorynames();
                Set<String> catuniq = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
                catuniq.addAll(cat);
                uniqcat.addAll(catuniq);


                if (et_catname.getText().toString().equals("")) {
                    et_catname.setError("Category Name Cannot Be Empty");
                }
                else if(uniqcat.contains(et_catname.getText().toString().toLowerCase()))
                {
                    et_catname.setError("Category Name Already Exists");
                }
                else
                {
                    System.out.println("id"+id);
                    Categories categories = new Categories(id,et_catname.getText().toString());
                    catnames.add(categories);
                    categoryDatabase.categoryDao().insertCategory(categories);
                    adapter.notifyDataSetChanged();

                    finish();
                    startActivity(getIntent());
                }
            }
        });

        tv_catcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_cattext.setVisibility(View.VISIBLE);
                rv_cat.setVisibility(View.VISIBLE);
                fab_addcat.setVisibility(View.VISIBLE);

                ll_createcat.setVisibility(View.GONE);

                finish();
                startActivity(getIntent());
            }
        });

    }
}