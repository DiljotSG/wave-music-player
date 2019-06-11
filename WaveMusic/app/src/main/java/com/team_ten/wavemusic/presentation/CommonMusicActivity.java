package com.team_ten.wavemusic.presentation;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.logic.PlaybackController;

// This abstract class shares common methods that are
// Used in multiple activities, it's children can access these calls.
public abstract class CommonMusicActivity extends AppCompatActivity
{

	/**
	 * Creates the music controls for this activity.
	 */
	public void createMusicControls()
	{
		final Button skip_button = findViewById(R.id.skip_button);
		final Button play_button = findViewById(R.id.play_button);
		final Button pause_button = findViewById(R.id.pause_button);
		final Button back_button = findViewById(R.id.back_button);
		final Button skip_back_button = findViewById(R.id.skip_back_button);

		// Button behaviours
		skip_button.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				PlaybackController.playNext();
			}
		});
		play_button.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				PlaybackController.startSong();
			}
		});
		pause_button.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				PlaybackController.pause();
			}
		});
		back_button.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				PlaybackController.restart();
			}
		});
		skip_back_button.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				PlaybackController.playPrev();
			}
		});
	}
}
