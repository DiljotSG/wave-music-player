package com.team_ten.wavemusic;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.os.Environment;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

public class MainActivity extends AppCompatActivity
{
	// Constants
	private static final String LIBRARY_NAME = "User library";
	private static final String DEFAULT_LOCATION = "Music";
	private static final int PERMISSIONS_REQUEST_READ_STORAGE_CODE = 0;
	private static final int PERMISSIONS_REQUEST_WRITE_STORAGE_CODE = 1;

	// Private helper methods
	private String getExternalPath()
	{
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	private String getDefaultLocation()
	{
		return getExternalPath() + "/" + DEFAULT_LOCATION + "/";
	}

	/**
	 * Sets the permissions of the android app for reading/writing files.
	 */
	private void getFilePermissions()
	{
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED)
		{
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
					PERMISSIONS_REQUEST_READ_STORAGE_CODE);
		}
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED)
		{
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
					PERMISSIONS_REQUEST_WRITE_STORAGE_CODE);
		}
	}

	/**
	 * Builds the library view for the user's songs in the default path.
	 */
	private void buildLibraryView()
	{
		// Start building the user's library
		final FakeDatabase databaseController = new FakeDatabase();

		// Build the user library based off the default location
		buildUserLibrary(databaseController, getDefaultLocation());

		// Populate the list view in the main activity
		ListView listView = findViewById(R.id.list_songs);

		// To connect the playlist with the ListView using ArrayAdapter
		ArrayAdapter<Song> listAdapter = new ArrayAdapter<>(
				this,
				android.R.layout.simple_list_item_1,
				databaseController.getLibrary()
		);
		listView.setAdapter(listAdapter);

		// When a song in the ListView is clicked,
		// start a new NowPlaying activity and pass the song's title and URI into it.
		AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Intent intent = new Intent(MainActivity.this, NowPlayingActivity.class);
				intent.putExtra("title", databaseController.getSong(position).getName());
				intent.putExtra("URI", databaseController.getSong(position).getURI());
				startActivity(intent);
			}
		};
		listView.setOnItemClickListener(itemClickListener);
	}

	/**
	 * Purpose: Builds a playlist representing the user's library given a playlist and the directory to scan.
	 *
	 * @param databaseController The databaseController object to populate.
	 * @param directoryToScan    A String that represents the absolute path to the directory to scan.
	 */
	private void buildUserLibrary(FakeDatabase databaseController, String directoryToScan)
	{
		MusicDirectoryManager scanner = new MusicDirectoryManager(directoryToScan);

		while (scanner.hasNext())
		{
			Song currentSong = scanner.getNextSong();
			databaseController.addSong(currentSong);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// Default code on creation of an activity.
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Limit the code in this method to high level method calls only.

		getFilePermissions();
		buildLibraryView();
		// TODO: build the library view asynchronously, so we can have a progress bar (for building)
	}
}
