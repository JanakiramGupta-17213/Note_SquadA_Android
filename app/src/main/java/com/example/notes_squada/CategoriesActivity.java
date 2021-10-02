package com.example.notes_squada;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {

    TextView tv_cattext,tv_catsave,tv_catcancel;
    RecyclerView rv_cat;
    EditText et_catname;
    FloatingActionButton fab_addcat;
    ArrayList<String> catnames = new ArrayList<String>();

    LinearLayout ll_createcat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        getSupportActionBar().setTitle("Categories");

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