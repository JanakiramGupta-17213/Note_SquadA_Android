package com.example.notes_squada;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import java.io.IOException;
import com.example.androidrecording.audio.AudioRecordingHandler;
import com.example.androidrecording.audio.AudioRecordingThread;
import com.example.androidrecording.visualizer.VisualizerView;
import com.example.androidrecording.visualizer.renderer.BarGraphRenderer;
import com.example.androidrecordingtest.utils.NotificationUtils;
import com.example.androidrecordingtest.utils.StorageUtils;


public class AudioRecord {

    private static String fileName = null;

    private Button recordBtn, playBtn;
    private VisualizerView visualizerView;

    private AudioRecordingThread recordingThread;
    private boolean startRecording = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_rec);

        if (!StorageUtils.checkExternalStorageAvailable()) {
            NotificationUtils.showInfoDialog(this, getString(R.string.noExtStorageAvailable));
            return;
        }
        fileName = StorageUtils.getFileName(true);

        recordBtn = (Button) findViewById(R.id.recordBtn);
        recordBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                record();
            }
        });

        playBtn = (Button) findViewById(R.id.playBtn);
        playBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

        visualizerView = (VisualizerView) findViewById(R.id.visualizerView);
        setupVisualizer();
    }

    @Override
    protected void onPause() {
        super.onPause();

        recordStop();
    }

    @Override
    protected void onDestroy() {
        recordStop();
        releaseVisualizer();

        super.onDestroy();
    }

    private void setupVisualizer() {
        Paint paint = new Paint();
        paint.setStrokeWidth(5f);
        paint.setAntiAlias(true);
        paint.setColor(Color.argb(200, 227, 69, 53));
        BarGraphRenderer barGraphRendererBottom = new BarGraphRenderer(2, paint, false);
        visualizerView.addRenderer(barGraphRendererBottom);
    }


    private void releaseVisualizer() {
        visualizerView.release();
        visualizerView = null;
    }

    private void record() {
        if (startRecording) {
            recordStart();
        }
        else {
            recordStop();
        }
    }

    private void recordStart() {
        startRecording();
        startRecording = false;
        recordBtn.setText(R.string.stopRecordBtn);
        playBtn.setEnabled(false);
    }

    private void recordStop() {
        stopRecording();
        startRecording = true;
        recordBtn.setText(R.string.recordBtn);
        playBtn.setEnabled(true);
    }

    private void startRecording() {
        recordingThread = new AudioRecordingThread(fileName, new AudioRecordingHandler() {
            @Override
            public void onFftDataCapture(final byte[] bytes) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (visualizerView != null) {
                            visualizerView.updateVisualizerFFT(bytes);
                        }
                    }
                });
            }

            @Override
            public void onRecordSuccess() {}

            @Override
            public void onRecordingError() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        recordStop();
                        NotificationUtils.showInfoDialog(AudioRecordingActivity.this, getString(R.string.recordingError));
                    }
                });
            }

            @Override
            public void onRecordSaveError() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        recordStop();
                        NotificationUtils.showInfoDialog(AudioRecordingActivity.this, getString(R.string.saveRecordError));
                    }
                });
            }
        });
        recordingThread.start();
    }

    private void stopRecording() {
        if (recordingThread != null) {
            recordingThread.stopRecording();
            recordingThread = null;
        }
    }

    private void play() {
        Intent i = new Intent(AudioRecordingActivity.this, AudioPlaybackActivity.class);
        i.putExtra(VideoPlaybackActivity.FileNameArg, fileName);
        startActivityForResult(i, 0);
    }
}

}
