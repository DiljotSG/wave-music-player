package com.team_ten.wavemusic.application;

import android.app.Activity;
import android.content.Intent;

import com.team_ten.wavemusic.logic.PlaybackController;
import com.team_ten.wavemusic.objects.Song;
import com.team_ten.wavemusic.persistence.DatabaseStub;
import com.team_ten.wavemusic.persistence.IDatabaseController;
import com.team_ten.wavemusic.presentation.ListActivity;
import com.team_ten.wavemusic.presentation.ListOfPlaylistsActivity;
import com.team_ten.wavemusic.presentation.MainMusicActivity;
import com.team_ten.wavemusic.presentation.NowPlayingMusicActivity;
import com.team_ten.wavemusic.presentation.SearchActivity;
import com.team_ten.wavemusic.presentation.SelectSongsActivity;
import com.team_ten.wavemusic.presentation.SinglePlaylistActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class ActivityController implements Serializable
{
	// Instance variables.
	private IDatabaseController databaseController;

	/**
	 * Builds a database representing the user's library given a database.
	 */
	public void buildUserLibrary()
	{
		databaseController = new DatabaseStub();
		MusicDirectoryManager scanner = new MusicDirectoryManager();
		while (scanner.hasNext())
		{
			Song currentSong = scanner.getNextSong();
			databaseController.addSong(currentSong);
		}
	}

	/**
	 * Starts an Activity to display songs.
	 * Since some Activities all display songs, and they receive common parameters, we start them
	 * in this single method.
	 *
	 * @param callerActivity: the parent Activity of the Activity to be started.
	 * @param typeOfRetrieve: the type of content to be displayed.
	 */
	public void startListActivity(
			final Activity callerActivity, final ListActivity.TypeOfRetrieve typeOfRetrieve)
	{
		// Build the view on the UI thread (can't be done from the background).
		callerActivity.runOnUiThread(new Runnable()
		{
			@Override public void run()
			{
				ArrayList<Song> songList = databaseController.getLibrary();
				Intent intent = null;

				// start different Activity based on the typeOfRetrieve
				if (typeOfRetrieve == ListActivity.TypeOfRetrieve.MY_LIBRARY)
				{
					intent = new Intent(callerActivity, ListActivity.class);
				}
				else if (typeOfRetrieve == ListActivity.TypeOfRetrieve.ARTIST)
				{
					intent = new Intent(callerActivity, ListActivity.class);
				}
				else if (typeOfRetrieve == ListActivity.TypeOfRetrieve.ALBUM)
				{
					intent = new Intent(callerActivity, ListActivity.class);
				}
				else if (typeOfRetrieve == ListActivity.TypeOfRetrieve.PLAYLIST)
				{
					intent = new Intent(callerActivity, ListOfPlaylistsActivity.class);
				}
				else if (typeOfRetrieve == ListActivity.TypeOfRetrieve.LIKED_SONG)
				{
					intent = new Intent(callerActivity, ListActivity.class);
				}
				else if (typeOfRetrieve == ListActivity.TypeOfRetrieve.SEARCH)
				{
					intent = new Intent(callerActivity, SearchActivity.class);
				}

				// Pass necessary data into the Intent and start the Activity.
				PlaybackController.setPlaybackQueue(songList);
				intent.putExtra("listSongs", songList);
				intent.putExtra("TypeOfRetrieve", typeOfRetrieve.toString());
				intent.putExtra("activityController", ActivityController.this);
				callerActivity.startActivity(intent);
			}
		});
	}

	/**
	 * Starts a NowPlayingActivity
	 *
	 * @param callerActivity: the parent Activity of the NowPlayingActivity to be started.
	 * @param song:           The song to be played in the NowPlayingActivity.
	 * @param title:          The title of the song.
	 * @param uri:            The URI of the song.
	 */
	public void startNowPlayingActivity(
			final Activity callerActivity, Song song, String title, String uri)
	{
		Intent intent = new Intent(callerActivity, NowPlayingMusicActivity.class);
		intent.putExtra("song", song);
		callerActivity.startActivity(intent);
	}

	/**
	 * Starts a MainActivity
	 *
	 * @param callerActivity:     the parent Activity of the MainActivity to be started.
	 * @param activityController: The activityController object we will be using during the whole
	 *                            lifecycle of this app.
	 */
	public void startMainActivity(
			final Activity callerActivity, ActivityController activityController)
	{
		Intent intent = new Intent(callerActivity, MainMusicActivity.class);
		intent.putExtra("activityController", activityController);
		callerActivity.startActivity(intent);
	}

	/**
	 * Starts a SelectSongsActivity
	 *
	 * @param callerActivity:      the parent Activity of the SelectSongsActivity to be started.
	 * @param nameOfPlaylist:      The name of the playlist into which we select songs and add.
	 * @param isCreateNewPlaylist: whether we are selecting songs to create a new playlist or to
	 *                             add them into an existing playlist.
	 */
	public void startSelectSongsActivity(
			final Activity callerActivity, String nameOfPlaylist, boolean isCreateNewPlaylist)
	{
		ArrayList<Song> songList = databaseController.getLibrary();
		Intent intent = new Intent(callerActivity, SelectSongsActivity.class);
		intent.putExtra("listSongs", songList);
		intent.putExtra("nameOfPlaylist", nameOfPlaylist);
		intent.putExtra("activityController", ActivityController.this);
		intent.putExtra("isCreateNewPlaylist", isCreateNewPlaylist);
		callerActivity.startActivity(intent);
	}

	/**
	 * Starts a SinglePlaylistActivity
	 *
	 * @param callerActivity: the parent Activity of the SinglePlaylistActivity to be started.
	 * @param nameOfPlaylist: The name of the playlist to be displayed.
	 * @param songList:       The list of songs in that playlist.
	 */
	public void startSinglePlaylistActivity(
			final Activity callerActivity, String nameOfPlaylist, ArrayList<Song> songList)
	{
		Intent intent = new Intent(callerActivity, SinglePlaylistActivity.class);
		intent.putExtra("listSongs", songList);
		intent.putExtra("nameOfPlaylist", nameOfPlaylist);
		intent.putExtra("activityController", ActivityController.this);
		callerActivity.startActivity(intent);
	}
}