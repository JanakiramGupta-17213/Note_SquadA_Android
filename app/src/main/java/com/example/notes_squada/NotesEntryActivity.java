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

EEpublic class NotesEntryActivity extends AppCompatActivity implements LocationListener {

    EditText et_title, et_description;

    ImageView imv_voice, imv_image;

    Button btn_image, btn_save, btn_stoprecording;

    NotesDatabase notesDatabase;

    LocationManager locationManager;

    MediaRecorder recorder;

    List<String> notes_title = new ArrayList<String>();

    int id;
    String title, description, category, date, audiopath, imagepath, latitude, longitude;

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
        btn_stoprecording = findViewById(R.id.btn_stoprecording);

        notesDatabase = Room.databaseBuilder(getApplicationContext(), NotesDatabase.class, "notes").allowMainThreadQueries().build();
        List<Integer> idlist = notesDatabase.notesDao().getallid();

        notes_title = notesDatabase.notesDao().getalltitles();

        if (idlist.isEmpty()) {
            id = 1;
        } else {
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
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.
                        Media.EXTERNAL_CONTENT_URI);
                someActivityResultLauncher.launch(intent);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> uniqtitle = new ArrayList<>();
                Set<String> titleuniq = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
                titleuniq.addAll(notes_title);
                uniqtitle.addAll(titleuniq);

                if (et_title.getText().toString().equals("")) {
                    et_title.setError("Please enter a title");
                }
                else if(uniqtitle.contains(et_title.getText().toString().toLowerCase()))
                {
                    et_title.setError("Title already exists");
                }
                else if (et_description.getText().toString().equals("")) {
                    et_description.setError("Please enter description");
                }

                else {
                    System.out.println(date);
                    title = et_title.getText().toString();
                    description = et_description.getText().toString();
                    if(!(new File(audiopath).exists()))
                    {
                        audiopath="empty";
                    }
                    if(imv_image.getDrawable() == null)
                    {
                        imagepath="empty";
                    }

                    Notes notes = new Notes(id,title,description,category,String.valueOf(date),latitude,
                            longitude,audiopath,imagepath);

                    notesDatabase.notesDao().InsertNotes(notes);
                    finish();
                }
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