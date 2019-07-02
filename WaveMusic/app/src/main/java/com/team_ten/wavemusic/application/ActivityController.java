package com.team_ten.wavemusic.application;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.team_ten.wavemusic.logic.PlaybackController;
import com.team_ten.wavemusic.objects.Song;
import com.team_ten.wavemusic.presentation.ListActivity;
import com.team_ten.wavemusic.presentation.ListOfPlaylistsActivity;
import com.team_ten.wavemusic.presentation.MainMusicActivity;
import com.team_ten.wavemusic.presentation.NowPlayingMusicActivity;
import com.team_ten.wavemusic.presentation.SelectSongsActivity;
import com.team_ten.wavemusic.presentation.SinglePlaylistActivity;
import com.team_ten.wavemusic.logic.AccessLikes;
import com.team_ten.wavemusic.logic.AccessSong;
import com.team_ten.wavemusic.logic.AccessPlaylist;

import java.io.Serializable;
import java.util.ArrayList;

public class ActivityController implements Serializable
{
	//	// Instance variables.
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
			Log.v("test", "test");
			Song currentSong = scanner.getNextSong();
			accessSong.addSong(currentSong);
		}
	}

	/**
	 * Likes a song in the DB.
	 *
	 * @param songToLike Song that is to be Liked.
	 */
	public static void likeSong(Song songToLike)
	{
		accessLikes.likeSong(songToLike);
	}

	/**
	 * Unlikes a song in the DB.
	 *
	 * @param songToUnlike Song that is to be Unliked.
	 */
	public static void unlikeSong(Song songToUnlike)
	{
		accessLikes.unlikeSong(songToUnlike);
	}

	/**
	 * Get a list of all liked Songs.
	 *
	 * @returns An array list of liked Songs.
	 */
	public static ArrayList<Song> getLikedSongs()
	{
		return accessLikes.getLikedSongs();
	}

	/**
	 * Adds a playlist to the DB.
	 *
	 * @param name The name of the playlist to add
	 */
	public static void addPlaylist(String name)
	{
		// todo: check for duplicate entry
		accessPlaylist.addPlaylist(name);
	}

	/**
	 * Remove the given playlist.
	 *
	 * @param playlistName The name of the playlist to remove.
	 */
	public static void removePlaylist(String playlistName)
	{
		accessPlaylist.removePlaylist(playlistName);
	}

	/**
	 * Adds a Song to a Playlist in  the DB via the PLAYLIST_SONGS table
	 *
	 * @param song         song to add to playlist
	 * @param playlistName the playlist to add the song to
	 */
	public static void addSongToPlaylist(Song song, String playlistName)
	{
		accessPlaylist.addSongToPlaylist(song, playlistName);
	}

	/**
	 * Removes the given song from the given playlist
	 *
	 * @param song         The song to be removed.
	 * @param playlistName The playlist to remove the song from.
	 */
	public static void removeSongFromPlaylist(Song song, String playlistName)
	{
		accessPlaylist.removeSongFromPlaylist(song, playlistName);
	}

	/**
	 * Gets all of the playlist names.
	 *
	 * @return An array list of playlist names
	 */
	public static ArrayList<String> getAllPlaylists()
	{
		return accessPlaylist.getAllPlaylists();
	}

	/**
	 * Gets all of the songs in a playlist.
	 *
	 * @return An array list of playlist names
	 */
	public static ArrayList<Song> getSongsFromPlaylist(String playlistName)
	{
		return accessPlaylist.getSongsFromPlaylist(playlistName);
	}

	/**
	 * Return the length of the playlist (number of songs)
	 *
	 * @param playlistName the playlist to add the song to
	 */
	public static int getPlaylistLength(String playlistName)
	{
		return accessPlaylist.getPlaylistLength(playlistName);
	}

	/**
	 * Adds a Song to the DB.
	 *
	 * @param theSong The song to add.
	 */
	public static void addSong(Song theSong)
	{
		accessSong.addSong(theSong);
	}

	/**
	 * Returns a Song object for the given URI.
	 *
	 * @param songURI The URI of the Song.
	 *
	 * @return A Song object for the Song.
	 */
	public static Song getSong(String songURI)
	{
		return accessSong.getSong(songURI);
	}

	/**
	 * Gets every song in the library.
	 *
	 * @return Returns an ArrayList of every Song.
	 */
	public static ArrayList<Song> getAllSongs()
	{
		return accessSong.getAllSongs();
	}

	/**
	 * Removes a Song from the user's library.
	 *
	 * @param toRemove The song to remove from the user's library.
	 */
	public static void removeSong(Song toRemove)
	{
		accessSong.removeSong(toRemove);
	}

	/**
	 * Gets all of the artists names.
	 *
	 * @return An array list of artists names
	 */
	public static ArrayList<String> getAllArtists()
	{
		return accessSong.getAllArtists();
	}

	/**
	 * Gets all of the album names.
	 *
	 * @return An array list of album names
	 */
	public static ArrayList<String> getAllAlbums()
	{
		return accessSong.getAllAlbums();
	}

	/**
	 * Returns a all songs in a given album.
	 *
	 * @param albumName The URI of the Song.
	 *
	 * @return An array list of songs in the album.
	 */
	public static ArrayList<Song> getSongsFromAlbum(String albumName)
	{
		return accessSong.getSongsFromAlbum(albumName);
	}

	/**
	 * Returns a all songs from a given artist.
	 *
	 * @param artistName The name of the artist.
	 *
	 * @return An array list of songs by the artist.
	 */
	public static ArrayList<Song> getSongsFromArtist(String artistName)
	{
		return accessSong.getSongsFromArtist(artistName);
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
					intent.putExtra("listSongs", songList);
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
					intent.putExtra("listSongs", likedSongsList);
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

		Intent intent = new Intent(callerActivity, SelectSongsActivity.class);
		intent.putExtra("listSongs", songList);
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

		Intent intent = new Intent(callerActivity, SinglePlaylistActivity.class);
		intent.putExtra("listSongs", songList);
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

		intent.putExtra("TypeOfRetrieve", ListActivity.TypeOfRetrieve.MY_LIBRARY);
		intent.putExtra("listSongs", songList);
		intent.putExtra("nameOfPlaylist", contentForRetrieve);
		callerActivity.startActivity(intent);
	}
}
