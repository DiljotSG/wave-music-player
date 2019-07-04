package com.team_ten.wavemusic.presentation;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.team_ten.wavemusic.objects.AppSettings;
import com.team_ten.wavemusic.objects.PlaybackQueue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainMusicActivity extends CommonMusicActivity
{
	// Instance variables
	private SampleAssetManager sampleAssetManager;
	private PermissionManager permissionManager;
	@Override protected void onCreate(Bundle savedInstanceState)
	{
		// Default code on creation of an activity.
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Get permissions
		permissionManager = new PermissionManager(this);
		permissionManager.getFilePermissions();

		// We can't proceed until we have read permissions to set up the user's library.
		// The app is useless until these permissions are granted so we just spin.
		// The permissions are set asynchronously by a popup.
		int readPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);;
		boolean readAccess = readPerm == PackageManager.PERMISSION_GRANTED;

		while (!readAccess) {
			// Get the current permissions.
			readPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
			readAccess = readPerm == PackageManager.PERMISSION_GRANTED;
		}

		// Extract the assets
		sampleAssetManager = new SampleAssetManager(this);
		sampleAssetManager.extractMusicAssets();

		// Build the DB, hide buttons, show loading screen
		findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
		hideAllButtons();
		buildDatabase();

		PlaybackController.init(new MediaPlayer(), new PlaybackQueue());
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		AppSettings.init((AudioManager) getSystemService(Context.AUDIO_SERVICE));

		// Create the controls
		createLibraryViewControls();
		createMusicControls();
	}

	/**
	 * Shows all the buttons on the main view.
	 */
	public void showAllButtons()
	{
		findViewById(R.id.skip_button).setVisibility(View.VISIBLE);
		findViewById(R.id.play_button).setVisibility(View.VISIBLE);
		findViewById(R.id.pause_button).setVisibility(View.VISIBLE);
		findViewById(R.id.back_button).setVisibility(View.VISIBLE);
		findViewById(R.id.skip_back_button).setVisibility(View.VISIBLE);
		findViewById(R.id.myLibrary).setVisibility(View.VISIBLE);
		findViewById(R.id.playLists).setVisibility(View.VISIBLE);
		findViewById(R.id.artists).setVisibility(View.VISIBLE);
		findViewById(R.id.albums).setVisibility(View.VISIBLE);
		findViewById(R.id.likedSongs).setVisibility(View.VISIBLE);
	}

	/**
	 * Hides all the buttons on the main view.
	 */
	public void hideAllButtons()
	{
		findViewById(R.id.skip_button).setVisibility(View.GONE);
		findViewById(R.id.play_button).setVisibility(View.GONE);
		findViewById(R.id.pause_button).setVisibility(View.GONE);
		findViewById(R.id.back_button).setVisibility(View.GONE);
		findViewById(R.id.skip_back_button).setVisibility(View.GONE);
		findViewById(R.id.myLibrary).setVisibility(View.GONE);
		findViewById(R.id.playLists).setVisibility(View.GONE);
		findViewById(R.id.artists).setVisibility(View.GONE);
		findViewById(R.id.albums).setVisibility(View.GONE);
		findViewById(R.id.likedSongs).setVisibility(View.GONE);
	}

	/**
	 * Builds the user library view asynchronously.
	 */
	private void buildDatabase()
	{
		final MainMusicActivity thisView = this;

		// Build the database asynchronously.
		new Thread(new Runnable()
		{
			@Override public void run()
			{
				copyDatabaseToDevice();
				ActivityController.buildUserLibrary(thisView);
			}
		}).start();
	}

	/**
	 * Copies the DB to the device (from the sample project).
	 */
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

		}
		catch (final IOException ioe)
		{
			Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
		}
	}

	/**
	 * Copies the DB assets to the device (from the sample project).
	 */
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

	/**
	 * Creates the controls for accessing the library
	 */
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