package com.team_ten.wavemusic.presentation.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.application.ActivityController;
import com.team_ten.wavemusic.logic.PlaybackController;
import com.team_ten.wavemusic.objects.music.Song;
import com.team_ten.wavemusic.presentation.interfaces.PlaybackCallback;
import com.team_ten.wavemusic.presentation.other.Themes;

import utils.EspressoIdlingResource;

import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_NO;
import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_YES;

// This abstract class shares common methods that are
// Used in multiple activities, it's children can access these calls.
@SuppressWarnings("ALL") public abstract class CommonMusicActivity extends PlaybackCallback
{
	private boolean isLight = true;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		isLight = Themes.getIsLight();
		if(isLight)
		{
			AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);
		}
		else
		{
			AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
		}
	}

	@Override protected void onResume()
	{
		super.onResume();

		if(isLight != Themes.getIsLight())
		{
			recreate();
		}
	}

	/**
	 * Creates the music controls for this activity.
	 */
	void createMusicControls()
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

	/**
	 * Finalize the options menu
	 */
	@Override public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu, menu);
		if (Themes.getIsLight())
		{
			menu.getItem(0).setIcon(R.drawable.ic_wb_sunny_black_24dp);
		}
		else
		{
			menu.getItem(0).setIcon(R.drawable.ic_brightness_3_black_24dp);
		}
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Handler for item selection.
	 *
	 * @return if the item is selected
	 */
	@Override public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();

		if (id == R.id.search)
		{
			new Thread(new Runnable()
			{
				@Override public void run()
				{
					ActivityController.startListActivity(CommonMusicActivity.this,
														 ListActivity.TypeOfRetrieve.SEARCH);
				}
			}).start();
		}
		else if (id == R.id.changeTheme)
		{
			Themes.changeTheme(this);
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * For acceptance testing only, to get IdlingResource object.
	 */
	@VisibleForTesting public IdlingResource getIdlingResource()
	{
		return EspressoIdlingResource.getIdlingResource();
	}
}
