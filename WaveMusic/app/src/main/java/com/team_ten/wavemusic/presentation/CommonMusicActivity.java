package com.team_ten.wavemusic.presentation;

import android.view.View;
import android.widget.ImageButton;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.logic.PlaybackController;
import com.team_ten.wavemusic.objects.Song;

// This abstract class shares common methods that are
// Used in multiple activities, it's children can access these calls.
public abstract class CommonMusicActivity extends PlaybackCallback
{

	/**
	 * Creates the music controls for this activity.
	 */
	public void createMusicControls()
	{
		final ImageButton skip_button = findViewById(R.id.skip_button);
		final ImageButton play_button = findViewById(R.id.play_button);
		final ImageButton pause_button = findViewById(R.id.pause_button);
		final ImageButton back_button = findViewById(R.id.back_button);
		final ImageButton skip_back_button = findViewById(R.id.skip_back_button);

		// Button behaviours
		skip_button.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Song song = PlaybackController.playNext();
				afterNext(song);
			}
		});
		play_button.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Song song = PlaybackController.startSong();
				afterPlay(song);
			}
		});
		pause_button.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				PlaybackController.pause();
				afterPause();
			}
		});
		back_button.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Song song = PlaybackController.restart();
				afterRestart(song);
			}
		});
		skip_back_button.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Song song = PlaybackController.playPrev();
				afterBack(song);
			}
		});
	}
}
