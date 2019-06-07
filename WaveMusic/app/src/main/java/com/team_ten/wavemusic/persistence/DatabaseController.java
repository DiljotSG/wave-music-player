package com.team_ten.wavemusic.persistence;

import com.team_ten.wavemusic.objects.Song;

import java.util.ArrayList;

public interface DatabaseController
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
	 *
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
	 * @param songName Name of the song to get.
	 *
	 * @return The matching Song or null if the song is not found.
	 */
	Song getSong(String songName);

	/**
	 * Purpose: Get the song at the specified index
	 *
	 * @param index The index in songList to get the song from.
	 *
	 * @return The Song at the specified index.
	 */
	Song getSong(int index);

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
