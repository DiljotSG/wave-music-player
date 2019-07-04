package com.team_ten.wavemusic.persistence.stubs;

import com.team_ten.wavemusic.objects.Song;
import com.team_ten.wavemusic.persistence.IPlaylistPersistence;

import java.util.ArrayList;
import java.util.HashMap;

public class PlaylistPersistenceStub implements IPlaylistPersistence
{
	private ArrayList<String> listOfPlaylists;
	private HashMap<String, ArrayList<Song>> playlists;

	public PlaylistPersistenceStub()
	{
		listOfPlaylists = new ArrayList<>();
		playlists = new HashMap<>();
	}

	/**
	 * Adds a playlist to the DB.
	 *
	 * @param playlistName The name of the playlist to add
	 */
	public void addPlaylist(String playlistName)
	{
		if (!listOfPlaylists.contains(playlistName))
		{
			listOfPlaylists.add(playlistName);
		}
	}

	/**
	 * Remove the given playlist.
	 *
	 * @param playlistName The name of the playlist to remove.
	 */
	public void removePlaylist(String playlistName)
	{
		listOfPlaylists.remove(playlistName);
	}

	/**
	 * Adds a Song to a Playlist in  the DB via the PLAYLIST_SONGS table
	 *
	 * @param song         song to add to playlist
	 * @param playlistName the playlist to add the song to
	 */
	public void addSongToPlaylist(Song song, String playlistName)
	{
		if (playlists.containsKey(playlistName))
		{
			ArrayList<Song> existingList = playlists.get(playlistName);
			if (existingList != null && !existingList.contains(song))
			{
				existingList.add(song);
			}
			playlists.replace(playlistName, existingList);
		}
		else
		{
			ArrayList<Song> newList = new ArrayList<>();
			newList.add(song);
			playlists.put(playlistName, newList);
		}
	}

	/**
	 * Removes the given song from the given playlist
	 *
	 * @param song         The song to be removed.
	 * @param playlistName The playlist to remove the song from.
	 */
	public void removeSongFromPlaylist(Song song, String playlistName)
	{
		if (playlists.containsKey(playlistName))
		{
			ArrayList<Song> existingList = playlists.get(playlistName);
			if (existingList != null)
			{
				existingList.remove(song);
			}
			playlists.replace(playlistName, existingList);
		}
	}

	/**
	 * Gets all of the playlist names.
	 *
	 * @return An array list of playlist names
	 */
	public ArrayList<String> getAllPlaylists()
	{
		return listOfPlaylists;
	}

	/**
	 * Gets all of the songs in a playlist.
	 *
	 * @return An array list of playlist names.
	 */
	public ArrayList<Song> getSongsFromPlaylist(String playlistName)
	{
		return playlists.get(playlistName);
	}

}
