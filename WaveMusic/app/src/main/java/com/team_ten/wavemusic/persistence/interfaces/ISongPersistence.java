package com.team_ten.wavemusic.persistence.interfaces;

import com.team_ten.wavemusic.objects.music.Song;

import java.util.ArrayList;

public interface ISongPersistence
{
	/**
	 * Adds a Song to the DB.
	 *
	 * @param theSong The song to add.
	 */
	void addSong(Song theSong);

	/**
	 * Removes a Song from the user's library.
	 *
	 * @param toRemove The song to remove from the user's library.
	 */
	void removeSong(Song toRemove);

	/**
	 * Returns a Song object for the given URI.
	 *
	 * @param songURI The URI of the Song.
	 *
	 * @return A Song object for the Song.
	 */
	Song getSong(String songURI);

	/**
	 * Gets every song in the library.
	 *
	 * @return Returns an ArrayList of every Song.
	 */
	ArrayList<Song> getAllSongs();

	/**
	 * Gets all of the artists names.
	 *
	 * @return An array list of artists names
	 */
	ArrayList<String> getAllArtists();

	/**
	 * Gets all of the album names.
	 *
	 * @return An array list of album names
	 */
	ArrayList<String> getAllAlbums();

	/**
	 * Returns a all songs in a given album.
	 *
	 * @param albumName The URI of the Song.
	 *
	 * @return An array list of songs in the album.
	 */
	ArrayList<Song> getSongsFromAlbum(String albumName);

	/**
	 * Returns a all songs from a given artist.
	 *
	 * @param artistName The name of the artist.
	 *
	 * @return An array list of songs by the artist.
	 */
	ArrayList<Song> getSongsFromArtist(String artistName);
}
