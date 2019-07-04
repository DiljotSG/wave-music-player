package com.team_ten.wavemusic.presentation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.logic.AccessLikes;
import com.team_ten.wavemusic.logic.PlaybackController;
import com.team_ten.wavemusic.objects.AppSettings;
import com.team_ten.wavemusic.objects.Song;

import static android.net.Uri.*;

public class NowPlayingMusicActivity extends CommonMusicActivity
{
	// Song currently being played.
	private Song song;
	private SeekBar progressBar;
	private SeekBar volumeBar;

	@Override protected void onCreate(Bundle savedInstanceState)
	{
		// Default code on creation of an activity.
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_now_playing);


		initSeekBars();
		initLike();

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

	private void initSeekBars() {
		progressBar = findViewById(R.id.seekBarForMusic);
		progressBar.setMax(100);
		volumeBar = findViewById(R.id.seekBarForVolume);
		volumeBar.setMax(AppSettings.getMaxVolume());
		volumeBar.setProgress(AppSettings.getVolume());

		volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			@Override
			public void onStopTrackingTouch(SeekBar arg0)
			{
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0)
			{
			}

			@Override
			public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
			{
				AppSettings.setVolume(progress);
			}
		});

		progressBar.setVisibility(View.INVISIBLE);
	}

	private void initLike() {
		final ImageView img = findViewById(R.id.likeImg);

		img.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AccessLikes clickAccess = new AccessLikes();

				if (clickAccess.getLikedSongs().contains(song)) {
					clickAccess.unlikeSong(song);
				}
				else {
					clickAccess.likeSong(song);
				}
			}
		});

	}


	/**
	 * Sets the title of the NowPlayingActivity after a song is skipped forward.
	 *
	 * @param newSong The song that we switched to.
	 */
	@Override public void afterNext(Song newSong)
	{
		song = newSong;
		getSupportActionBar().setTitle(song.getName());
	}

	/**
	 * Sets the title of the NowPlayingActivity after a song is played.
	 *
	 * @param newSong The song that we switched to.
	 */
	@Override public void afterPlay(Song newSong)
	{
		song = newSong;
		getSupportActionBar().setTitle(song.getName());
	}

	/**
	 * Sets the title of the NowPlayingActivity after a song is restarted.
	 *
	 * @param newSong The song that we switched to.
	 */
	@Override public void afterRestart(Song newSong)
	{
		song = newSong;
		getSupportActionBar().setTitle(song.getName());
	}

	/**
	 * Sets the title of the NowPlayingActivity after a song is skipped back.
	 *
	 * @param newSong The song that we switched to.
	 */
	@Override public void afterBack(Song newSong)
	{
		song = newSong;
		getSupportActionBar().setTitle(song.getName());
	}
}