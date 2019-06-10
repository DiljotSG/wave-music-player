package com.team_ten.wavemusic.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.logic.ActivityController;
import com.team_ten.wavemusic.application.PermissionManager;
import com.team_ten.wavemusic.application.SampleAssetManager;
import com.team_ten.wavemusic.logic.PlaybackController;

public class MainActivity extends AppCompatActivity {
    private void buildUserLibraryView(final ActivityController activityController) {
        // Build the view asynchronously.
        new Thread(new Runnable() {
            @Override
            public void run() {
                activityController.buildLibraryView();
            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Default code on creation of an activity.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button skip_button = findViewById(R.id.skip_button);
        final Button play_button = findViewById(R.id.play_button);
        final Button pause_button = findViewById(R.id.pause_button);
        final Button back_button = findViewById(R.id.back_button);
        final Button skip_back_button = findViewById(R.id.skip_back_button);

        PermissionManager permissionManager = new PermissionManager(this);
        ActivityController activityController = new ActivityController(this);
        SampleAssetManager sampleAssetManager = new SampleAssetManager(this);
        PlaybackController.init();

        // Limit the code in this method to high level method calls only.
        permissionManager.getFilePermissions();
        sampleAssetManager.extractMusicAssets();
        buildUserLibraryView(activityController);

        // Button behaviours
        skip_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PlaybackController.playNext();
            }
        });
        play_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PlaybackController.startSong();
            }
        });
        pause_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PlaybackController.pause();
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PlaybackController.restart();
            }
        });
        skip_back_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PlaybackController.playPrev();
            }
        });
    }
}