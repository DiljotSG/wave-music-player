package com.team_ten.wavemusic.application;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.team_ten.wavemusic.logic.AccessLikes;
import com.team_ten.wavemusic.logic.AccessPlaylist;
import com.team_ten.wavemusic.logic.AccessSong;
import com.team_ten.wavemusic.logic.MusicDirectoryManager;
import com.team_ten.wavemusic.logic.PlaybackController;
import com.team_ten.wavemusic.objects.Library;
import com.team_ten.wavemusic.objects.Song;
import com.team_ten.wavemusic.persistence.hsqldb.WaveDBIntegrityConstraintException;
import com.team_ten.wavemusic.presentation.ListActivity;
import com.team_ten.wavemusic.presentation.ListOfPlaylistsActivity;
import com.team_ten.wavemusic.presentation.MainMusicActivity;
import com.team_ten.wavemusic.presentation.NowPlayingMusicActivity;
import com.team_ten.wavemusic.presentation.SelectSongsActivity;
import com.team_ten.wavemusic.presentation.SinglePlaylistActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class ActivityController implements Serializable
{
	// Instance variables.
	private static AccessSong accessSong;
	private static AccessPlaylist accessPlaylist;
	private static AccessLikes accessLikes;

	/**
	 * Builds a database representing the user's library given a database.
	 */
	public static void buildUserLibrary()
	{
		accessSong = new AccessSong();
		accessPlaylist = new AccessPlaylist();
		accessLikes = new AccessLikes();
		MusicDirectoryManager scanner = new MusicDirectoryManager();
		while (scanner.hasNext())
		{
			Song currentSong = scanner.getNextSong();

			try
			{
				accessSong.addSong(currentSong);
			}
			catch (WaveDBIntegrityConstraintException e)
			{
				Log.v("Dup", "Duplicate song attempted to be added to library");
			}
		}
	}

	/**
	 * Getter for accessSong.
	 * @return accessSong
	 */
	public static AccessSong getAccessSong()
	{
		return accessSong;
	}

	/**
	 * Getter for accessPlaylist.
	 * @return accessPlaylist
	 */
	public static AccessPlaylist getAccessPlaylist()
	{
		return accessPlaylist;
	}

	/**
	 * Getter for accessPlaylist.
	 * @return accessPlaylist
	 */
	public static AccessLikes getAccessLikes()
	{
		return accessLikes;
	}

	/**
	 * Starts an Activity to display songs.
	 * Since some Activities all display songs, and they receive common parameters, we start them
	 * in this single method.
	 *
	 * @param callerActivity: the parent Activity of the Activity to be started.
	 * @param typeOfRetrieve: the type of content to be displayed.
	 */
	public static void startListActivity(
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
					intent = new Intent(callerActivity, ListActivity.class);
					ArrayList<Song> songList = accessSong.getAllSongs();
					Library.setCurLibrary(songList);
					PlaybackController.setPlaybackQueue(songList);
				}
				else if (typeOfRetrieve == ListActivity.TypeOfRetrieve.ARTIST)
				{
					intent = new Intent(callerActivity, ListActivity.class);
					ArrayList<String> artistList = accessSong.getAllArtists();
					intent.putExtra("listStrings", artistList);
				}
				else if (typeOfRetrieve == ListActivity.TypeOfRetrieve.ALBUM)
				{
					intent = new Intent(callerActivity, ListActivity.class);
					ArrayList<String> albumList = accessSong.getAllAlbums();
					intent.putExtra("listStrings", albumList);
				}
				else if (typeOfRetrieve == ListActivity.TypeOfRetrieve.PLAYLIST)
				{
					intent = new Intent(callerActivity, ListOfPlaylistsActivity.class);
					ArrayList<String> playlistList = accessPlaylist.getAllPlaylists();
					intent.putExtra("listStrings", playlistList);
				}
				else if (typeOfRetrieve == ListActivity.TypeOfRetrieve.LIKED_SONG)
				{
					intent = new Intent(callerActivity, ListActivity.class);
					ArrayList<Song> likedSongsList = accessLikes.getLikedSongs();
					Library.setCurLibrary(likedSongsList);
					PlaybackController.setPlaybackQueue(likedSongsList);
				}

				// Pass necessary data into the Intent and start the Activity.
				intent.putExtra("TypeOfRetrieve", typeOfRetrieve.toString());
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
	public static void startNowPlayingActivity(
			final Activity callerActivity, Song song, String title, String uri)
	{
		Intent intent = new Intent(callerActivity, NowPlayingMusicActivity.class);
		intent.putExtra("song", song);
		callerActivity.startActivity(intent);
	}

	/**
	 * Starts a MainActivity
	 */
	public static void startMainActivity(
			final Activity callerActivity)
	{
		Intent intent = new Intent(callerActivity, MainMusicActivity.class);
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
	public static void startSelectSongsActivity(
			final Activity callerActivity, String nameOfPlaylist, boolean isCreateNewPlaylist)
	{
		ArrayList<Song> songList = accessSong.getAllSongs();
		Library.setCurLibrary(songList);
		Intent intent = new Intent(callerActivity, SelectSongsActivity.class);
		intent.putExtra("nameOfPlaylist", nameOfPlaylist);
		intent.putExtra("isCreateNewPlaylist", isCreateNewPlaylist);
		callerActivity.startActivity(intent);
	}

	/**
	 * Starts a SinglePlaylistActivity
	 *
	 * @param callerActivity: the parent Activity of the SinglePlaylistActivity to be started.
	 * @param nameOfPlaylist: The name of the playlist to be displayed.
	 */
	public static void startSinglePlaylistActivity(
			final Activity callerActivity, String nameOfPlaylist)
	{
		ArrayList<Song> songList = accessPlaylist.getSongsFromPlaylist(nameOfPlaylist);
		Library.setCurLibrary(songList);

		Intent intent = new Intent(callerActivity, SinglePlaylistActivity.class);
		intent.putExtra("nameOfPlaylist", nameOfPlaylist);
		callerActivity.startActivity(intent);
	}

	/**
	 * Starts a ListActivity based on the result of retrieve
	 *
	 * @param callerActivity:     the parent Activity of the SinglePlaylistActivity to be started.
	 * @param typeOfRetrieve:     The name of the playlist to be displayed.
	 * @param contentForRetrieve: The list of songs in that playlist.
	 */
	public static void startAlbumOrArtistAct(
			final Activity callerActivity, String typeOfRetrieve, String contentForRetrieve)
	{
		Intent intent = new Intent(callerActivity, ListActivity.class);
		ArrayList<Song> songList = null;

		if (typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.ALBUM.toString()))
		{
			songList = accessSong.getSongsFromAlbum(contentForRetrieve);
		}
		else if (typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.ARTIST.toString()))
		{
			songList = accessSong.getSongsFromArtist(contentForRetrieve);
		}

		Library.setCurLibrary(songList);

		intent.putExtra("TypeOfRetrieve", ListActivity.TypeOfRetrieve.MY_LIBRARY);
		intent.putExtra("nameOfPlaylist", contentForRetrieve);
		callerActivity.startActivity(intent);
	}
}
