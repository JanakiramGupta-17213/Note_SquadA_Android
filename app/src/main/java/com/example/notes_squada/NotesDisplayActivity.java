package com.example.notes_squada;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Size;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.notes_squada.Database.CategoryDatabase;
import com.example.notes_squada.Database.Notes;
import com.example.notes_squada.Database.NotesDatabase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotesDisplayActivity extends AppCompatActivity implements OnMapReadyCallback {

    NotesDatabase notesDatabase;

    CategoryDatabase categoryDatabase;

    int id;

    String title, description, categoryname, date, audiopath, imagepath, latitude, longitude;

    List<String> categories = new ArrayList<String>();

    Notes note;

    TextView tv_title,tv_description,tv_category,tv_categorytext;

    Button btn_update,btn_play;

    Spinner spinner_category;

    ImageView imv_notesimage;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_display);

        title = getIntent().getStringExtra("title");
        id = getIntent().getIntExtra("id",1);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        notesDatabase = Room.databaseBuilder(getApplicationContext(),NotesDatabase.class,"notes").allowMainThreadQueries().build();
        categoryDatabase = Room.databaseBuilder(getApplicationContext(), CategoryDatabase.class,"category").allowMainThreadQueries().build();

        note = notesDatabase.notesDao().getnotesbyid(id);
        categories = categoryDatabase.categoryDao().getCategorynames();

        description = note.getDescription();
        categoryname = note.getCategory();
        date = note.getDate();
        audiopath = note.getAudiopath();
        imagepath = note.getImagepath();
        latitude = note.getLatitude();
        longitude = note.getLongitude();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tv_title = findViewById(R.id.tv_title);
        tv_description = findViewById(R.id.tv_description);
        tv_category = findViewById(R.id.tv_category);
        tv_categorytext = findViewById(R.id.tv_categorytext);

        spinner_category = findViewById(R.id.spinner_category);

        imv_notesimage = findViewById(R.id.imv_notesimage);

        btn_update = findViewById(R.id.btn_update);
        btn_play = findViewById(R.id.btn_play);

        tv_title.setText(title);
        tv_description.setText(description);
        tv_category.setText(categoryname);

        if(categories.size() == 1)
        {
            tv_categorytext.setVisibility(View.GONE);
            spinner_category.setVisibility(View.GONE);
        }
        else
        {
            String[] categoriesarray = new String[categories.size()];
            for(int i = 0; i<categories.size(); i++)
            {
                categoriesarray[i] = categories.get(i);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_child,categoriesarray);
            spinner_category.setAdapter(adapter);

            int position = adapter.getPosition(categoryname);
            spinner_category.setSelection(position);
        }

        if(!imagepath.equals("empty"))
        {
            Bitmap thumbnail = null;
            try {
                thumbnail = getApplicationContext().getContentResolver().loadThumbnail(Uri.parse(imagepath),
                        new Size(400,400),null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("image path is:"+imagepath);
            imv_notesimage.setImageBitmap(thumbnail);
        }


        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String temp = adapterView.getSelectedItem().toString();

                if(!temp.equals(categoryname))
                {
                    btn_update.setVisibility(View.VISIBLE);
                    categoryname = temp;
                    note.setCategory(categoryname);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notesDatabase.notesDao().UpdateNotes(note);
                btn_update.setVisibility(View.GONE);
            }
        });

        if(audiopath.equals("empty"))
        {
            btn_play.setVisibility(View.GONE);
        }

        if(imagepath.equals("empty"))
        {
            imv_notesimage.setVisibility(View.GONE);
        }

        btn_play.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetWorldReadable")
            @Override
            public void onClick(View view) {
                File file = new File(audiopath);
                file.setReadable(true, false);
                FileInputStream fisAudio = null;
                try {
                    fisAudio = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    assert fisAudio != null;
                    mediaPlayer.setDataSource(fisAudio.getFD());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        LatLng latlng = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
        googleMap.addMarker(new MarkerOptions()
                .position(latlng)
                .title(title));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16.0f));
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