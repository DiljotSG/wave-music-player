package com.team_ten.wavemusic.logic;

import com.team_ten.wavemusic.application.Services;
import com.team_ten.wavemusic.objects.Song;
import com.team_ten.wavemusic.persistence.ISongPersistence;

import java.util.ArrayList;

public class AccessSong
{
	private ISongPersistence songPersistence;

	public AccessSong()
	{
		songPersistence = Services.getSongPersistence();
	}

	public AccessSong(final ISongPersistence songPersistence)
	{
		this();
		this.songPersistence = songPersistence;
	}

	/**
	 * Adds a Song to the DB.
	 *
	 * @param theSong The song to add.
	 */
	public void addSong(Song theSong)
	{
		songPersistence.addSong(theSong);
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
		return songPersistence.getSong(songURI);
	}

	/**
	 * Gets every song in the library.
	 *
	 * @return Returns an ArrayList of every Song.
	 */
	public ArrayList<Song> getAllSongs()
	{
		return songPersistence.getAllSongs();
	}

	/**
	 * Removes a Song from the user's library.
	 *
	 * @param toRemove The song to remove from the user's library.
	 */
	public void removeSong(Song toRemove)
	{
		songPersistence.removeSong(toRemove);
	}
}
