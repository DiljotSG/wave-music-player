package com.team_ten.wavemusic.persistence;

import com.team_ten.wavemusic.objects.Playlist;
import com.team_ten.wavemusic.objects.Song;

public interface IPlaylistPersistence
{
	/**
	 * Adds a playlist to the DB.
	 *
	 * @param name The name of the playlist to add
	 */
	public void addPlaylist(String name);

	/**
	 * Return the length of the playlist (number of songs)
	 *
	 * @param playlist the playlist to add the song to
	 */
	public int getLength(Playlist playlist);

	/**
	 * Adds a Song to a Playlist in  the DB via the PLAYLIST_SONGS table
	 *
	 * @param song song to add to playlist
	 * @param playlist the playlist to add the song to
	 */
	public void addSong(Song song, Playlist playlist);
}
