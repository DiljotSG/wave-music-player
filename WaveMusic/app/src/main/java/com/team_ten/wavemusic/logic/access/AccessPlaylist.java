package com.team_ten.wavemusic.logic.access;

import com.team_ten.wavemusic.application.db.Services;
import com.team_ten.wavemusic.objects.music.Song;
import com.team_ten.wavemusic.persistence.interfaces.IPlaylistPersistence;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class uses:
 * ___  ___ ___ ___ _  _ ___  ___ _  _  _____   __  ___ _  _    _ ___ ___ _____ ___ ___  _  _
 * |   \| __| _ \ __| \| |   \| __| \| |/ __\ \ / / |_ _| \| |_ | | __/ __|_   _|_ _/ _ \| \| |
 * | |) | _||  _/ _|| .` | |) | _|| .` | (__ \ V /   | || .` | || | _| (__  | |  | | (_) | .` |
 * |___/|___|_| |___|_|\_|___/|___|_|\_|\___| |_|   |___|_|\_|\__/|___\___| |_| |___\___/|_|\_|
 */
public class AccessPlaylist implements IPlaylistPersistence, Serializable
{
	private IPlaylistPersistence playlistPersistence;

	public AccessPlaylist()
	{
		this.playlistPersistence = Services.getPlaylistPersistence();
	}

	public AccessPlaylist(final IPlaylistPersistence playlistPersistence)
	{
		this();
		this.playlistPersistence = playlistPersistence;
	}

	/**
	 * Adds a playlist to the DB.
	 *
	 * @param name The name of the playlist to add
	 */
	public void addPlaylist(String name)
	{
		playlistPersistence.addPlaylist(name);
	}

	/**
	 * Remove the given playlist.
	 *
	 * @param playlistName The name of the playlist to remove.
	 */
	public void removePlaylist(String playlistName)
	{
		playlistPersistence.removePlaylist(playlistName);
	}

	/**
	 * Adds a Song to a Playlist in  the DB via the PLAYLIST_SONGS table
	 *
	 * @param song         song to add to playlist
	 * @param playlistName the playlist to add the song to
	 */
	public void addSongToPlaylist(Song song, String playlistName)
	{
		playlistPersistence.addSongToPlaylist(song, playlistName);
	}

	/**
	 * Removes the given song from the given playlist
	 *
	 * @param song         The song to be removed.
	 * @param playlistName The playlist to remove the song from.
	 */
	public void removeSongFromPlaylist(Song song, String playlistName)
	{
		playlistPersistence.removeSongFromPlaylist(song, playlistName);
	}

	/**
	 * Gets all of the playlist names.
	 *
	 * @return An array list of playlist names
	 */
	public ArrayList<String> getAllPlaylists()
	{
		return playlistPersistence.getAllPlaylists();
	}

	/**
	 * Gets all of the songs in a playlist.
	 *
	 * @return An array list of playlist names
	 */
	public ArrayList<Song> getSongsFromPlaylist(String playlistName)
	{
		return playlistPersistence.getSongsFromPlaylist(playlistName);
	}
}
