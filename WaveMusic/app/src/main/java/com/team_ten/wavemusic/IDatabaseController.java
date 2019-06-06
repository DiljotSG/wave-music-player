package com.team_ten.wavemusic;

import java.util.ArrayList;

public interface IDatabaseController
{
	/***
	 * Purpose: Add a song to the database.
	 *
	 * @param song Song to add to the database.
	 */
	void addSong(Song song);

	/**
	 * Purpose: Get all songs from the specified artist.
	 *
	 * @param artist The name of the artist.
	 * @return An ArrayList of Songs from the specified artist.
	 */
	ArrayList<Song> getSongsFromArtist(String artist);

	/***
	 * Purpose: Get all songs from the specified album.
	 *
	 * @param album The name of the album.
	 * @return An ArrayList of Songs from the specified album.
	 */
	ArrayList<Song> getSongsFromAlbum(String album);

	/**
	 * Purpose: Get the specified song from the library.
	 *
	 * @param song Song to get.
	 * @return An ArrayList containing the specified song, or empty if the song is not present.
	 */
	Song getSong(Song song);

	/**
	 * Purpose: Get the library of songs.
	 *
	 * @return An ArrayList of Songs.
	 */
	ArrayList<Song> getLibrary();

	/**
	 * Purpose: Checks if the library exists.
	 *
	 * @return A Boolean specifying if the library exists or does not exist.
	 */
	Boolean hasLibrary();
}
