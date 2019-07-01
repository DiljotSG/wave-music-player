package com.team_ten.wavemusic.logic;

import com.team_ten.wavemusic.application.Services;
import com.team_ten.wavemusic.objects.Playlist;
import com.team_ten.wavemusic.objects.Song;
import com.team_ten.wavemusic.persistence.IPlaylistPersistence;

public class AccessPlaylist implements IPlaylistPersistence
{
	private IPlaylistPersistence playlistPersistence;

	public AccessPlaylist()
	{
		playlistPersistence = Services.getPlaylistPersistence();
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
		// todo: check for duplicate entry
		playlistPersistence.addPlaylist(name);
	}

	/**
	 * Return the length of the playlist (number of songs)
	 *
	 * @param playlist the playlist to add the song to
	 */
	public int getLength(Playlist playlist)
	{
		return playlistPersistence.getLength(playlist);
	}

	/**
	 * Adds a Song to a Playlist in  the DB via the PLAYLIST_SONGS table
	 *
	 * @param song song to add to playlist
	 * @param playlist the playlist to add the song to
	 */
	public void addSong(Song song, Playlist playlist)
	{
		playlistPersistence.addSong(song, playlist);
	}
}
