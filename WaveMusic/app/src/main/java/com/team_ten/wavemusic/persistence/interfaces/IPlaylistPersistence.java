package com.team_ten.wavemusic.persistence.interfaces;

import com.team_ten.wavemusic.objects.music.Song;

import java.util.ArrayList;

public interface IPlaylistPersistence
{
	/**
	 * Adds a playlist to the DB.
	 *
	 * @param playlistName The name of the playlist to add
	 */
	public void addPlaylist(String playlistName);

	/**
	 * Remove the given playlist.
	 *
	 * @param playlistName The name of the playlist to remove.
	 */
	public void removePlaylist(String playlistName);

	/**
	 * Adds a Song to a Playlist in  the DB via the PLAYLIST_SONGS table
	 *
	 * @param song         song to add to playlist
	 * @param playlistName the playlist to add the song to
	 */
	public void addSongToPlaylist(Song song, String playlistName);

	/**
	 * Removes the given song from the given playlist
	 *
	 * @param song         The song to be removed.
	 * @param playlistName The playlist to remove the song from.
	 */
	public void removeSongFromPlaylist(Song song, String playlistName);

	/**
	 * Gets all of the playlist names.
	 *
	 * @return An array list of playlist names
	 */
	public ArrayList<String> getAllPlaylists();

	/**
	 * Gets all of the songs in a playlist.
	 *
	 * @return An array list of playlist names.
	 */
	public ArrayList<Song> getSongsFromPlaylist(String playlistName);
}
