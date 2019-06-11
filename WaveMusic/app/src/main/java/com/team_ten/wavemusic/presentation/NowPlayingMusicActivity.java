package com.team_ten.wavemusic.presentation;

import android.content.Intent;
import android.os.Bundle;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.logic.PlaybackController;
import com.team_ten.wavemusic.objects.Song;


public class NowPlayingMusicActivity extends CommonMusicActivity
{
	private Song song;    // Song currently being played

	@Override protected void onCreate(Bundle savedInstanceState)
	{
		// Default code on creation of an activity.
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_now_playing);

		// To get the title and URI from the intent.
		Intent intent = getIntent();
		song = (Song) intent.getSerializableExtra("song");

		if (song == null)
		{
			throw (new NullPointerException());
		}

		// Limit the code in this method to high level method calls only.
		// Let the action bar display the title of the song playing.
		getSupportActionBar().setTitle(song.getName());
		PlaybackController.startSong(song);
		createMusicControls();
	}
}