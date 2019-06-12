package com.team_ten.wavemusic.logic;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.objects.Song;
import com.team_ten.wavemusic.persistence.DatabaseStub;
import com.team_ten.wavemusic.persistence.IDatabaseController;
import com.team_ten.wavemusic.presentation.MainMusicActivity;
import com.team_ten.wavemusic.presentation.NowPlayingMusicActivity;

import java.util.ArrayList;

public class ActivityController
{
	// Instance variables.
	private MainMusicActivity mainView;

	/**
	 * The constructor for the ActivityController class.
	 *
	 * @param mainActivity The activity that is the main application activity.
	 */
	public ActivityController(MainMusicActivity mainActivity)
	{
		mainView = mainActivity;
	}

	/**
	 * Builds the library view for the user's songs in the default path.
	 */
	public void buildLibraryView()
	{
		// Start building the user's library.
		final DatabaseStub databaseController = new DatabaseStub();

		// Build the user library based off the default location.
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

	// Private helper methods.

	/**
	 * Builds a database representing the user's library given a database.
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
	 * Actually populates the main view with the user's library.
	 *
	 * @param databaseController The IDatabaseController to pull songs from.
	 */
	private void updateMainView(final IDatabaseController databaseController)
	{
		// Populate the list view in the main activity.
		ListView listView = mainView.findViewById(R.id.list_songs);

		ArrayList<Song> songList = databaseController.getLibrary();
		PlaybackController.setPlaybackQueue(songList);

		// To connect the playlist with the ListView using ArrayAdapter.
		ArrayAdapter<Song> listAdapter = new ArrayAdapter<>(mainView,
															android.R.layout.simple_list_item_1,
															songList);
		listView.setAdapter(listAdapter);

		// When a song in the ListView is clicked, start a new NowPlaying activity and pass the
		// song's title and URI into it.
		AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Song selectedSong = databaseController.getSong(position);
				Intent intent = new Intent(mainView, NowPlayingMusicActivity.class);
				intent.putExtra("song", selectedSong);
				intent.putExtra("title", selectedSong.getName());
				intent.putExtra("URI", selectedSong.getURI());
				mainView.startActivity(intent);
			}
		};
		listView.setOnItemClickListener(itemClickListener);

		mainView.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
	}
}