package com.team_ten.wavemusic.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.logic.PlaybackController;
import com.team_ten.wavemusic.objects.Song;


public class NowPlayingActivity extends AppCompatActivity {
    private Song song;    // Song currently being played

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Default code on creation of an activity.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        final Button skip_button = findViewById(R.id.skip_button);
        final Button play_button = findViewById(R.id.play_button);
        final Button pause_button = findViewById(R.id.pause_button);
        final Button back_button = findViewById(R.id.back_button);
        final Button skip_back_button = findViewById(R.id.skip_back_button);

        // To get the title and URI from the intent.
        Intent intent = getIntent();
        song = (Song) intent.getSerializableExtra("song");

        // todo: replace with custom exception
        if (song == null)
            throw (new NullPointerException());

        // Let the action bar display the title of the song playing.
        getSupportActionBar().setTitle(song.getName());

        PlaybackController.startSong(song);

        // Button behaviours
        skip_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                song = PlaybackController.playNext();
                getSupportActionBar().setTitle(song.getName());
            }
        });
        play_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                song = PlaybackController.startSong();
                getSupportActionBar().setTitle(song.getName());
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
                song = PlaybackController.playPrev();
                getSupportActionBar().setTitle(song.getName());
            }
        });
    }
}