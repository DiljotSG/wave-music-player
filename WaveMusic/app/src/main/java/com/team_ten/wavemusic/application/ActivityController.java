package com.team_ten.wavemusic.application;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.team_ten.wavemusic.logic.stubs.LikesPersistenceStub;
import com.team_ten.wavemusic.logic.stubs.PlaylistPersistenceStub;
import com.team_ten.wavemusic.logic.stubs.SongPersistenceStub;
import com.team_ten.wavemusic.objects.Song;
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
//	// Instance variables.
	private AccessSong accessSong;
	private AccessPlaylist accessPlaylist;
	private AccessLikes accessLikes;

	public ActivityController()
	{
		accessSong = new AccessSong();
		accessPlaylist = new AccessPlaylist();
		accessLikes = new AccessLikes();
	}

	/**
	 * Builds a database representing the user's library given a database.
	 */
	public void buildUserLibrary()
	{
		MusicDirectoryManager scanner = new MusicDirectoryManager();
		while (scanner.hasNext())
		{
			Song currentSong = scanner.getNextSong();
			accessSong.addSong(currentSong);
		}
	}

	/**
	 * Likes a song in the DB.
	 *
	 * @param songToLike Song that is to be Liked.
	 */
	public void likeSong(Song songToLike)
	{
		accessLikes.likeSong(songToLike);
	}

	/**
	 * Unlikes a song in the DB.
	 *
	 * @param songToUnlike Song that is to be Unliked.
	 */
	public void unlikeSong(Song songToUnlike)
	{
		accessLikes.unlikeSong(songToUnlike);
	}

	/**
	 * Get a list of all liked Songs.
	 *
	 * @returns An array list of liked Songs.
	 */
	public ArrayList<Song> getLikedSongs()
	{
		return accessLikes.getLikedSongs();
	}

	/**
	 * Adds a playlist to the DB.
	 *
	 * @param name The name of the playlist to add
	 */
	public void addPlaylist(String name)
	{
		// todo: check for duplicate entry
		accessPlaylist.addPlaylist(name);
	}

	/**
	 * Remove the given playlist.
	 *
	 * @param playlistName The name of the playlist to remove.
	 */
	public void removePlaylist(String playlistName)
	{
		accessPlaylist.removePlaylist(playlistName);
	}

	/**
	 * Adds a Song to a Playlist in  the DB via the PLAYLIST_SONGS table
	 *
	 * @param song         song to add to playlist
	 * @param playlistName the playlist to add the song to
	 */
	public void addSongToPlaylist(Song song, String playlistName)
	{
		accessPlaylist.addSongToPlaylist(song, playlistName);
	}

	/**
	 * Removes the given song from the given playlist
	 *
	 * @param song         The song to be removed.
	 * @param playlistName The playlist to remove the song from.
	 */
	public void removeSongFromPlaylist(Song song, String playlistName)
	{
		accessPlaylist.removeSongFromPlaylist(song, playlistName);
	}

	/**
	 * Gets all of the playlist names.
	 *
	 * @return An array list of playlist names
	 */
	public ArrayList<String> getAllPlaylists()
	{
		return accessPlaylist.getAllPlaylists();
	}

	/**
	 * Gets all of the songs in a playlist.
	 *
	 * @return An array list of playlist names
	 */
	public ArrayList<Song> getSongsFromPlaylist(String playlistName)
	{
		return accessPlaylist.getSongsFromPlaylist(playlistName);
	}

	/**
	 * Return the length of the playlist (number of songs)
	 *
	 * @param playlistName the playlist to add the song to
	 */
	public int getPlaylistLength(String playlistName)
	{
		return accessPlaylist.getPlaylistLength(playlistName);
	}

	/**
	 * Adds a Song to the DB.
	 *
	 * @param theSong The song to add.
	 */
	public void addSong(Song theSong)
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
	public Song getSong(String songURI)
	{
		return accessSong.getSong(songURI);
	}

	/**
	 * Gets every song in the library.
	 *
	 * @return Returns an ArrayList of every Song.
	 */
	public ArrayList<Song> getAllSongs()
	{
		return accessSong.getAllSongs();
	}

	/**
	 * Removes a Song from the user's library.
	 *
	 * @param toRemove The song to remove from the user's library.
	 */
	public void removeSong(Song toRemove)
	{
		accessSong.removeSong(toRemove);
	}

	/**
	 * Gets all of the artists names.
	 *
	 * @return An array list of artists names
	 */
	public ArrayList<String> getAllArtists()
	{
		return accessSong.getAllArtists();
	}

	/**
	 * Gets all of the album names.
	 *
	 * @return An array list of album names
	 */
	public ArrayList<String> getAllAlbums()
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
	public ArrayList<Song> getSongsFromAlbum(String albumName)
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
	public ArrayList<Song> getSongsFromArtist(String artistName)
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
	public void startListActivity(
			final Activity callerActivity, final ListActivity.TypeOfRetrieve typeOfRetrieve)
	{
		// Build the view on the UI thread (can't be done from the background).
		callerActivity.runOnUiThread(new Runnable()
		{
			@Override public void run()
			{
				Intent intent = null;


					Log.v("qwe", "123");
					intent = new Intent(callerActivity, ListActivity.class);
					ArrayList<Song> songList = accessSong.getAllSongs();
					Log.v("qwe", ""+songList.size());
					intent.putExtra("listSongs", songList);
					PlaybackController.setPlaybackQueue(songList);
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
			songList = accessSong.getSongsFromAlbum(contentForRetrieve);
		}
		else if(typeOfRetrieve.equals(ListActivity.TypeOfRetrieve.ARTIST.toString()))
		{
			songList = accessSong.getSongsFromArtist(contentForRetrieve);
		}

		intent.putExtra("TypeOfRetrieve", ListActivity.TypeOfRetrieve.MY_LIBRARY);
		intent.putExtra("listSongs", songList);
		intent.putExtra("nameOfPlaylist", contentForRetrieve);
		intent.putExtra("activityController", ActivityController.this);
		callerActivity.startActivity(intent);
	}
}