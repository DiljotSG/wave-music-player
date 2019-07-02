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
	private ActivityController activityController;
	private SampleAssetManager sampleAssetManager;

	@Override protected void onCreate(Bundle savedInstanceState)
	{
		// Default code on creation of an activity.
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		copyDatabaseToDevice();

		activityController = (ActivityController) getIntent().getSerializableExtra(
				"activityController");
		permissionManager = new PermissionManager(this);
		sampleAssetManager = new SampleAssetManager(this);
		PlaybackController.init(new MediaPlayer(), new PlaybackQueue());

		// Limit the code in this method to high level method calls only.
		permissionManager.getFilePermissions();
		sampleAssetManager.extractMusicAssets();
		createMusicControls();
		createLibraryViewControls();
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
						activityController.startListActivity(MainMusicActivity.this,
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
						activityController.startListActivity(MainMusicActivity.this,
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
						activityController.startListActivity(MainMusicActivity.this,
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
						activityController.startListActivity(MainMusicActivity.this,
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
						activityController.startListActivity(MainMusicActivity.this,
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
					activityController.startListActivity(MainMusicActivity.this,
														 ListActivity.TypeOfRetrieve.SEARCH);
				}
			}).start();
		}
		return super.onOptionsItemSelected(item);
	}

	private void copyDatabaseToDevice()
	{
		final String DB_PATH = "db";

		String[] assetNames;
		Context context = getApplicationContext();
		File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
		AssetManager assetManager = getAssets();

		try
		{

			assetNames = assetManager.list(DB_PATH);
			for (int i = 0; i < assetNames.length; i++)
			{
				assetNames[i] = DB_PATH + "/" + assetNames[i];
			}

			copyAssetsToDirectory(assetNames, dataDirectory);

			Main.setDBPathName(dataDirectory.toString() + "/" + Main.getDBPathName());
			System.out.println(Main.getDBPathName());

		}
		catch (final IOException ioe)
		{
			Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
		}
	}

	public void copyAssetsToDirectory(String[] assets, File directory) throws IOException
	{
		AssetManager assetManager = getAssets();

		for (String asset : assets)
		{
			String[] components = asset.split("/");
			String copyPath = directory.toString() + "/" + components[components.length - 1];

			char[] buffer = new char[1024];
			int count;

			File outFile = new File(copyPath);

			if (!outFile.exists())
			{
				InputStreamReader in = new InputStreamReader(assetManager.open(asset));
				FileWriter out = new FileWriter(outFile);

				count = in.read(buffer);
				while (count != -1)
				{
					out.write(buffer, 0, count);
					count = in.read(buffer);
				}

				out.close();
				in.close();
			}
		}
	}
}