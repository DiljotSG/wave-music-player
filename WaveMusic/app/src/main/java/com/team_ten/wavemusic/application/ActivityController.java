package com.team_ten.wavemusic.application;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

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
//	// Instance variables.
//	private IDatabaseController databaseController;
//
	/**
	 * Builds a database representing the user's library given a database.
	 */
	public void buildUserLibrary()
	{
		MusicDirectoryManager scanner = new MusicDirectoryManager();
		AccessSong accessSong = new AccessSong();
		while (scanner.hasNext())
		{
			Song currentSong = scanner.getNextSong();
			accessSong.addSong(currentSong);
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
				Intent intent = null;

				// start different Activity based on the typeOfRetrieve
				if (typeOfRetrieve == ListActivity.TypeOfRetrieve.MY_LIBRARY ||
					typeOfRetrieve == ListActivity.TypeOfRetrieve.SEARCH)
				{
					Log.v("qwe", "123");
					AccessSong accessSong = new AccessSong();
					intent = new Intent(callerActivity, ListActivity.class);
					ArrayList<Song> songList = accessSong.getAllSongs();
					Log.v("qwe", ""+songList.size());
					intent.putExtra("listSongs", songList);
					PlaybackController.setPlaybackQueue(songList);
				}
				else if (typeOfRetrieve == ListActivity.TypeOfRetrieve.ARTIST)
				{
					AccessSong accessSong = new AccessSong();
					intent = new Intent(callerActivity, ListActivity.class);
					ArrayList<String> artistList = accessSong.getAllArtists();
					intent.putExtra("ListStrings", artistList);
				}
				else if (typeOfRetrieve == ListActivity.TypeOfRetrieve.ALBUM)
				{
					AccessSong accessSong = new AccessSong();
					intent = new Intent(callerActivity, ListActivity.class);
					ArrayList<String> albumList = accessSong.getAllAlbums();
					intent.putExtra("ListStrings", albumList);
				}
				else if (typeOfRetrieve == ListActivity.TypeOfRetrieve.PLAYLIST)
				{
					AccessPlaylist accessPlaylist = new AccessPlaylist();
					intent = new Intent(callerActivity, ListOfPlaylistsActivity.class);
					ArrayList<String> playlistList = accessPlaylist.getAllPlaylists();
					intent.putExtra("ListStrings", playlistList);
				}
				else if (typeOfRetrieve == ListActivity.TypeOfRetrieve.LIKED_SONG)
				{
					AccessLikes accessLikes = new AccessLikes();
					intent = new Intent(callerActivity, ListActivity.class);
					ArrayList<Song> likedSongsList = accessLikes.getLikedSongs();
					intent.putExtra("listSongs", likedSongsList);
					PlaybackController.setPlaybackQueue(likedSongsList);
				}

				// Pass necessary data into the Intent and start the Activity.
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
		AccessSong accessSong = new AccessSong();
		ArrayList<Song> songList = accessSong.getAllSongs();

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
	 */
	public void startSinglePlaylistActivity(
			final Activity callerActivity, String nameOfPlaylist)
	{
		AccessPlaylist accessPlaylist = new AccessPlaylist();
		ArrayList<Song> songList = accessPlaylist.getSongsFromPlaylist(nameOfPlaylist);

		Intent intent = new Intent(callerActivity, SinglePlaylistActivity.class);
		intent.putExtra("listSongs", songList);
		intent.putExtra("nameOfPlaylist", nameOfPlaylist);
		intent.putExtra("activityController", ActivityController.this);
		callerActivity.startActivity(intent);
	}

	/**
	 * Starts a ListActivity based on the result of retrieve
	 *
	 * @param callerActivity:     the parent Activity of the SinglePlaylistActivity to be started.
	 * @param typeOfRetrieve:     The name of the playlist to be displayed.
	 * @param contentForRetrieve: The list of songs in that playlist.
	 */
	public void startAlbumOrArtistAct(
			final Activity callerActivity,
			String typeOfRetrieve,
			String contentForRetrieve)
	{
		Intent intent = new Intent(callerActivity, ListActivity.class);
		ArrayList<Song> songList = null;

		if(typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.ALBUM.toString()))
		{
			AccessSong accessSong = new AccessSong();
			songList = accessSong.getSongsFromAlbum(contentForRetrieve);
		}
		else if(typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.ARTIST.toString()))
		{
			AccessSong accessSong = new AccessSong();
			songList = accessSong.getSongsFromArtist(contentForRetrieve);
		}

		intent.putExtra("TypeOfRetrieve", ListActivity.TypeOfRetrieve.MY_LIBRARY);
		intent.putExtra("listSongs", songList);
		intent.putExtra("nameOfPlaylist", contentForRetrieve);
		intent.putExtra("activityController", ActivityController.this);
		callerActivity.startActivity(intent);
	}
}