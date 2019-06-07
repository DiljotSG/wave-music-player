package com.team_ten.wavemusic.application;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.logic.MusicDirectoryManager;
import com.team_ten.wavemusic.objects.Song;
import com.team_ten.wavemusic.persistence.IDatabaseController;
import com.team_ten.wavemusic.persistence.DatabaseStub;
import com.team_ten.wavemusic.presentation.MainActivity;
import com.team_ten.wavemusic.presentation.NowPlayingActivity;

public class ActivityController
{
	// Instance variables
	private MainActivity mainView;

	/**
	 * Purpose: The constructor for the ActivityController class
	 *
	 * @param mainActivity The activity that is the main application activity.
	 */
	public ActivityController(MainActivity mainActivity)
	{
		mainView = mainActivity;
	}

	/**
	 * Builds the library view for the user's songs in the default path.
	 */
	public void buildLibraryView()
	{
		// Start building the user's library
		final DatabaseStub databaseController = new DatabaseStub();

		// Build the user library based off the default location
		buildUserLibrary(databaseController);

		// Build the view on the main UI thread (can't be done from the background).
		mainView.runOnUiThread(new Runnable()
		{
			@Override public void run()
			{
				updateMainView(databaseController);
			}
		});
	}

	// Private helper methods

	/**
	 * Purpose: Builds a database representing the user's library given a database.
	 *
	 * @param databaseController The databaseController object to populate.
	 */
	private void buildUserLibrary(DatabaseStub databaseController)
	{
		MusicDirectoryManager scanner = new MusicDirectoryManager();
		while (scanner.hasNext())
		{
			Song currentSong = scanner.getNextSong();
			databaseController.addSong(currentSong);
		}
	}

	/**
	 * Purpose: Actually populates the main view with the user's library.
	 *
	 * @param databaseController The IDatabaseController to pull songs from.
	 */
	private void updateMainView(final IDatabaseController databaseController)
	{
		// Populate the list view in the main activity
		ListView listView = mainView.findViewById(R.id.list_songs);

		// To connect the playlist with the ListView using ArrayAdapter
		ArrayAdapter<Song> listAdapter = new ArrayAdapter<>(
				mainView,
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
				Intent intent = new Intent(mainView, NowPlayingActivity.class);
				intent.putExtra("title", databaseController.getSong(position).getName());
				intent.putExtra("URI", databaseController.getSong(position).getURI());
				mainView.startActivity(intent);
			}
		};
		listView.setOnItemClickListener(itemClickListener);

		mainView.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
	}
}
