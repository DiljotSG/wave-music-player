package com.team_ten.wavemusic.presentation;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.application.Main;
import com.team_ten.wavemusic.application.PermissionManager;
import com.team_ten.wavemusic.application.SampleAssetManager;
import com.team_ten.wavemusic.application.ActivityController;
import com.team_ten.wavemusic.logic.PlaybackController;
import com.team_ten.wavemusic.objects.PlaybackQueue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainMusicActivity extends CommonMusicActivity
{
	private PermissionManager permissionManager;
	private SampleAssetManager sampleAssetManager;

	@Override protected void onCreate(Bundle savedInstanceState)
	{
		// Default code on creation of an activity.
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		permissionManager = new PermissionManager(this);
		sampleAssetManager = new SampleAssetManager(this);
		PlaybackController.init(new MediaPlayer(), new PlaybackQueue());

		// Limit the code in this method to high level method calls only.
		permissionManager.getFilePermissions();
		sampleAssetManager.extractMusicAssets();
		createLibraryViewControls();
		createMusicControls();
	}

	public void createLibraryViewControls()
	{
		final Button myLibrary_button = findViewById(R.id.myLibrary);
		final Button playlists_button = findViewById(R.id.playLists);
		final Button artists_button = findViewById(R.id.artists);
		final Button albums_button = findViewById(R.id.albums);
		final Button likedSongs_button = findViewById(R.id.likedSongs);

		// Button behaviours
		myLibrary_button.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				new Thread(new Runnable()
				{
					@Override public void run()
					{
						ActivityController.startListActivity(MainMusicActivity.this,
															 ListActivity.TypeOfRetrieve.MY_LIBRARY);
					}
				}).start();
			}
		});

		playlists_button.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				new Thread(new Runnable()
				{
					@Override public void run()
					{
						ActivityController.startListActivity(MainMusicActivity.this,
															 ListActivity.TypeOfRetrieve.PLAYLIST);
					}
				}).start();
			}
		});

		artists_button.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				new Thread(new Runnable()
				{
					@Override public void run()
					{
						ActivityController.startListActivity(MainMusicActivity.this,
															 ListActivity.TypeOfRetrieve.ARTIST);
					}
				}).start();
			}
		});

		albums_button.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				new Thread(new Runnable()
				{
					@Override public void run()
					{
						ActivityController.startListActivity(MainMusicActivity.this,
															 ListActivity.TypeOfRetrieve.ALBUM);
					}
				}).start();
			}
		});

		likedSongs_button.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				new Thread(new Runnable()
				{
					@Override public void run()
					{
						ActivityController.startListActivity(MainMusicActivity.this,
															 ListActivity.TypeOfRetrieve.LIKED_SONG);
					}
				}).start();
			}
		});
		findViewById(R.id.loadingPanel).setVisibility(View.GONE);
	}

	@Override public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	// handle button activities
	@Override public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();

		if (id == R.id.search)
		{
			new Thread(new Runnable()
			{
				@Override public void run()
				{
					ActivityController.startListActivity(MainMusicActivity.this,
														 ListActivity.TypeOfRetrieve.SEARCH);
				}
			}).start();
		}
		return super.onOptionsItemSelected(item);
	}
}