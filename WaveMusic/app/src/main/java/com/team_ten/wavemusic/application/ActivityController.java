package com.team_ten.wavemusic.application;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.team_ten.wavemusic.R;
import com.team_ten.wavemusic.logic.MusicDirectoryManager;
import com.team_ten.wavemusic.logic.access.AccessLikes;
import com.team_ten.wavemusic.logic.access.AccessPlaylist;
import com.team_ten.wavemusic.logic.access.AccessSong;
import com.team_ten.wavemusic.objects.exceptions.WaveEmptyLibraryException;
import com.team_ten.wavemusic.objects.music.Library;
import com.team_ten.wavemusic.objects.music.Song;
import com.team_ten.wavemusic.presentation.activities.ListActivity;
import com.team_ten.wavemusic.presentation.activities.ListOfPlaylistsActivity;
import com.team_ten.wavemusic.presentation.activities.MainMusicActivity;
import com.team_ten.wavemusic.presentation.activities.NowPlayingMusicActivity;
import com.team_ten.wavemusic.presentation.activities.SearchActivity;
import com.team_ten.wavemusic.presentation.activities.SelectSongsActivity;
import com.team_ten.wavemusic.presentation.activities.SinglePlaylistActivity;

import java.io.Serializable;
import java.util.ArrayList;

import utils.EspressoIdlingResource;

public class ActivityController implements Serializable
{
	// Instance variables
	private static AccessSong accessSong;
	private static AccessPlaylist accessPlaylist;
	private static AccessLikes accessLikes;

	/**
	 * Builds a database representing the user's library given a database.
	 */
	public static void buildUserLibrary(final MainMusicActivity mainMusicActivity)
	{
		accessSong = new AccessSong();
		accessPlaylist = new AccessPlaylist();
		accessLikes = new AccessLikes();

		try
		{
			MusicDirectoryManager scanner = new MusicDirectoryManager();

			while (scanner.hasNext())
			{
				Song currentSong = scanner.getNextSong();
				accessSong.addSong(currentSong);
			}
		}
		catch (WaveEmptyLibraryException e)
		{
			System.out.println(e.getMessage());
		}

		// Remove the loading screen on the UI thread (can't be done from the background).
		mainMusicActivity.runOnUiThread(new Runnable()
		{
			@Override public void run()
			{
				mainMusicActivity.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
				mainMusicActivity.showAllButtons();
				EspressoIdlingResource.decrement();
			}
		});
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
		// Build the view on the UI thread
		callerActivity.runOnUiThread(new Runnable()
		{
			@Override public void run()
			{
				Intent intent = null;

				try
				{
					// start different Activity based on the typeOfRetrieve
					if (typeOfRetrieve == ListActivity.TypeOfRetrieve.MY_LIBRARY)
					{
						intent = new Intent(callerActivity, ListActivity.class);
						ArrayList<Song> songList = accessSong.getAllSongs();
						Library.setCurSongLibrary(songList);
						intent.putExtra("title", "My Library");
					}
					else if (typeOfRetrieve == ListActivity.TypeOfRetrieve.SEARCH)
					{
						intent = new Intent(callerActivity, SearchActivity.class);
						ArrayList<Song> songList = accessSong.getAllSongs();
						intent.putExtra("title", "Search");
						Library.setCurSongLibrary(songList);
					}
					else if (typeOfRetrieve == ListActivity.TypeOfRetrieve.ARTIST)
					{
						intent = new Intent(callerActivity, ListActivity.class);
						ArrayList<String> artistList = accessSong.getAllArtists();
						Library.setCurStringLibrary(artistList);
						intent.putExtra("title", "Artists");
					}
					else if (typeOfRetrieve == ListActivity.TypeOfRetrieve.ALBUM)
					{
						intent = new Intent(callerActivity, ListActivity.class);
						ArrayList<String> albumList = accessSong.getAllAlbums();
						Library.setCurStringLibrary(albumList);
						intent.putExtra("title", "Albums");
					}
					else if (typeOfRetrieve == ListActivity.TypeOfRetrieve.PLAYLIST)
					{
						intent = new Intent(callerActivity, ListOfPlaylistsActivity.class);
						intent.putExtra("title", "Playlists");
					}
					else if (typeOfRetrieve == ListActivity.TypeOfRetrieve.LIKED_SONG)
					{
						intent = new Intent(callerActivity, ListActivity.class);
						ArrayList<Song> likedSongsList = accessLikes.getLikedSongs();
						Library.setCurSongLibrary(likedSongsList);
						intent.putExtra("title", "Liked Songs");
					}

					// Pass necessary data into the Intent and start the Activity.
					intent.putExtra("TypeOfRetrieve", typeOfRetrieve.toString());
					callerActivity.startActivity(intent);
				}
				catch (WaveEmptyLibraryException e)
				{
					System.out.println(e.getMessage());
				}
			}
		});
	}

	/**
	 * Starts a NowPlayingActivity
	 *
	 * @param callerActivity: the parent Activity of the NowPlayingActivity to be started.
	 * @param song:           The song to be played in the NowPlayingActivity.
	 */
	public static void startNowPlayingActivity(
			final Activity callerActivity, Song song)
	{
		Intent intent = new Intent(callerActivity, NowPlayingMusicActivity.class);
		intent.putExtra("song", song);
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
		Library.setCurSongLibrary(songList);
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
		Library.setCurSongLibrary(songList);

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

		Library.setCurSongLibrary(songList);

		intent.putExtra("TypeOfRetrieve", ListActivity.TypeOfRetrieve.MY_LIBRARY.toString());
		intent.putExtra("title", contentForRetrieve);
		callerActivity.startActivity(intent);
	}

	/**
	 * Getter for accessSong.
	 *
	 * @return accessSong
	 */
	public static AccessSong getAccessSong()
	{
		return accessSong;
	}

	/**
	 * Getter for accessPlaylist.
	 *
	 * @return accessPlaylist
	 */
	public static AccessPlaylist getAccessPlaylist()
	{
		return accessPlaylist;
	}

	/**
	 * Getter for accessPlaylist.
	 *
	 * @return accessPlaylist
	 */
	public static AccessLikes getAccessLikes()
	{
		return accessLikes;
	}
}
