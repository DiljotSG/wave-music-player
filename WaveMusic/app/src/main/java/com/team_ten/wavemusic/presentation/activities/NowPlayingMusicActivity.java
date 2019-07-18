package com.team_ten.wavemusic.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.application.ActivityController;
import com.team_ten.wavemusic.logic.PlaybackController;
import com.team_ten.wavemusic.objects.AppSettings;
import com.team_ten.wavemusic.objects.music.Song;

import java.util.Timer;
import java.util.TimerTask;

public class NowPlayingMusicActivity extends CommonMusicActivity
{
	// Song currently being played.
	private Song song;
	private SeekBar progressBar;
	private SeekBar volumeBar;
	private ImageView img;
	private ImageView shuffleimg; // Icon made by Dave Gandy from www.flaticon.com

	@Override protected void onCreate(Bundle savedInstanceState)
	{
		// Default code on creation of an activity.
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_now_playing);

		img = findViewById(R.id.likeImg);
		shuffleimg = findViewById(R.id.shuffleImg);

		// To get the title and URI from the intent.
		Intent intent = getIntent();
		song = (Song) intent.getSerializableExtra("song");

		if (song == null)
		{
			throw (new NullPointerException());
		}

		initSeekBars();
		initLike();

		// Limit the code in this method to high level method calls only.
		// Let the action bar display the title of the song playing.
		PlaybackController.setNowPlayingMusicActivity(this);
		PlaybackController.startSong(song);
		createMusicControls();

		updateInfo();
		createUpdateTimer();
	}

	private void createUpdateTimer()
	{
		new Timer().scheduleAtFixedRate(new TimerTask()
		{
			@Override public void run()
			{
				progressBar.setMax(PlaybackController.getPlaybackDuration());
				progressBar.setProgress(PlaybackController.getPlaybackPosition());
			}
		}, 0, 500);
	}

	public void setSong(Song song)
	{
		this.song = song;
	}

	public void updateInfo()
	{
		getSupportActionBar().setTitle(song.getName());
		TextView title = (TextView) findViewById(R.id.title);
		TextView album = (TextView) findViewById(R.id.album);
		TextView artist = (TextView) findViewById(R.id.artist);
		TextView genre = (TextView) findViewById(R.id.genre);

		String fullName = "Song: " + song.getName();
		String fullAlbum = "Album: " + song.getAlbum();
		String fullArtist = "Artist: " + song.getArtist();
		String fullGenre = "Genre: " + song.getGenre();

		title.setText(fullName);
		album.setText(fullAlbum);
		artist.setText(fullArtist);
		genre.setText(fullGenre);

		// Set the "Like" button to correct state based on if the playing song is liked or not.
		setStateOfLike();
	}

	/**
	 * Set listeners and values of seek bars
	 */
	private void initSeekBars()
	{
		progressBar = findViewById(R.id.seekBarForMusic);
		progressBar.setMax(PlaybackController.getPlaybackDuration());
		volumeBar = findViewById(R.id.seekBarForVolume);
		volumeBar.setMax(AppSettings.getMaxVolume());
		volumeBar.setProgress(AppSettings.getVolume());

		volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			@Override public void onStopTrackingTouch(SeekBar arg0)
			{
			}

			@Override public void onStartTrackingTouch(SeekBar arg0)
			{
			}

			@Override public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
			{
				AppSettings.setVolume(progress);
			}
		});

		progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			@Override public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser)
			{
				if (fromUser)
				{
					PlaybackController.seekTo(i);
				}
			}

			@Override public void onStartTrackingTouch(SeekBar seekBar)
			{

			}

			@Override public void onStopTrackingTouch(SeekBar seekBar)
			{

			}
		});
	}

	/**
	 * Set listeners and events for the Like button
	 */
	private void initLike()
	{
		// Set the "Like" button to correct state based on if the playing song is liked or not.
		setStateOfLike();

		img.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				if (ActivityController.getAccessLikes().getLikedSongs().contains(song))
				{
					img.setTag("R.drawable.ic_favorite_border_black_24dp");
					img.setImageResource(R.drawable.ic_favorite_border_black_24dp);
					ActivityController.getAccessLikes().unlikeSong(song);
				}
				else
				{
					img.setTag("R.drawable.ic_favorite_black_24dp");
					img.setImageResource(R.drawable.ic_favorite_black_24dp);
					ActivityController.getAccessLikes().likeSong(song);
				}
			}
		});

		shuffleimg.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				PlaybackController.toggleShuffle();

				if (PlaybackController.isShuffle())
				{
					shuffleimg.setAlpha(255);
				}
				else
				{
					shuffleimg.setAlpha(64);
				}
			}
		});
	}

	/**
	 * Set the state of "Like" button.
	 * If the song is liked, the icon of the button should be
	 * concrete, otherwise it should be hollow.
	 */
	private void setStateOfLike()
	{
		if (ActivityController.getAccessLikes().getLikedSongs().contains(song))
		{
			img.setTag("R.drawable.ic_favorite_black_24dp");
			img.setImageResource(R.drawable.ic_favorite_black_24dp);
		}
		else
		{
			img.setImageResource(R.drawable.ic_favorite_border_black_24dp);
			img.setTag("R.drawable.ic_favorite_border_black_24dp");
		}
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