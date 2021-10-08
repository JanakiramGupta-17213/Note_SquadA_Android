package com.example.notes_squada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CategoriesActivity extends AppCompatActivity {

    TextView tv_cattext,tv_catsave,tv_catcancel;
    RecyclerView rv_cat;
    EditText et_catname;
    FloatingActionButton fab_addcat;
    ArrayList<String> catnames;

    LinearLayout ll_createcat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        getSupportActionBar().setTitle("Categories");

        SharedPreferences pref = getSharedPreferences("Cat_Pref",MODE_PRIVATE);
        Set<String> fetchcat = pref.getStringSet("Cat_List",null);

        if(fetchcat == null)
        {
            catnames = new ArrayList<String>();
        }
        else
        {
            catnames = new ArrayList<String>(fetchcat);
        }

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
        }
        else
        {
            tv_cattext.setVisibility(View.GONE);
        }

        //Setting Layout Manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv_cat.setLayoutManager(layoutManager);

        //Setting Adapter
        CatListAdapter adapter = new CatListAdapter(catnames);
        rv_cat.setAdapter(adapter);

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
                if (et_catname.getText().toString().equals("")) {
                    et_catname.setError("Category Name Cannot Be Empty");
                }
                else
                {
                    catnames.add(et_catname.getText().toString());
                    SharedPreferences.Editor editor = getSharedPreferences("Cat_Pref",MODE_PRIVATE).edit();
                    Set<String> setcat = new HashSet<String>();
                    setcat.addAll(catnames);
                    editor.putStringSet("Cat_List",setcat);
                    editor.apply();

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