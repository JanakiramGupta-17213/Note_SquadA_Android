package com.example.notes_squada;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.room.Room;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notes_squada.Database.Notes;
import com.example.notes_squada.Database.NotesDatabase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class NotesEntryActivity extends AppCompatActivity {

    EditText et_title,et_description;

    ImageView imv_voice,imv_image;

    Button btn_image,btn_save;

    NotesDatabase notesDatabase;

    LocationManager locationManager;

    MediaRecorder recorder;

    List<String> notes_title = new ArrayList<String>();

    int id;
    String title,description,category,date,audiopath,videopath,latitude,longitude;

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        Uri imageuri = data.getData();
                        imagepath = imageuri.toString();
                        imv_image.setImageURI(imageuri);
                        System.out.println("selected image is :"+imageuri);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_entry);

        getSupportActionBar().setTitle("Enter Notes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        category = getIntent().getStringExtra("category");

        audiopath = getExternalCacheDir().getAbsolutePath();
        Date time = Calendar.getInstance().getTime();
        date = String.valueOf(time);
        audiopath += "/"+date+".3gp";

        latitude = String.valueOf(43.6274);
        longitude = String.valueOf(79.6745);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, NotesEntryActivity.this);

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

                recorder = new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                recorder.setOutputFile(audiopath);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                try {
                    recorder.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                recorder.start();

                btn_stoprecording.setVisibility(View.VISIBLE);
                btn_stoprecording.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recorder.stop();
                        recorder.release();
                        btn_stoprecording.setVisibility(View.GONE);
                    }
                });

                System.out.println("file name : "+audiopath);

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

    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());

        System.out.println("location is :"+latitude+"/"+longitude);
    }
}

}