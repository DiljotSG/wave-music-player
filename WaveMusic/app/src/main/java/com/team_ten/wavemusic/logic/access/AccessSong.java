package com.team_ten.wavemusic.logic.access;

import com.team_ten.wavemusic.application.db.Services;
import com.team_ten.wavemusic.objects.music.Song;
import com.team_ten.wavemusic.persistence.interfaces.ISongPersistence;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class uses:
 * ___  ___ ___ ___ _  _ ___  ___ _  _  _____   __  ___ _  _    _ ___ ___ _____ ___ ___  _  _
 * |   \| __| _ \ __| \| |   \| __| \| |/ __\ \ / / |_ _| \| |_ | | __/ __|_   _|_ _/ _ \| \| |
 * | |) | _||  _/ _|| .` | |) | _|| .` | (__ \ V /   | || .` | || | _| (__  | |  | | (_) | .` |
 * |___/|___|_| |___|_|\_|___/|___|_|\_|\___| |_|   |___|_|\_|\__/|___\___| |_| |___\___/|_|\_|
 */
public class AccessSong implements ISongPersistence, Serializable
{
	private ISongPersistence songPersistence;

	public AccessSong()
	{
		this.songPersistence = Services.getSongPersistence();
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

	/**
	 * Gets all of the artists names.
	 *
	 * @return An array list of artists names
	 */
	public ArrayList<String> getAllArtists()
	{
		return songPersistence.getAllArtists();
	}

	/**
	 * Gets all of the album names.
	 *
	 * @return An array list of album names
	 */
	public ArrayList<String> getAllAlbums()
	{
		return songPersistence.getAllAlbums();
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
		return songPersistence.getSongsFromAlbum(albumName);
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
		return songPersistence.getSongsFromArtist(artistName);
	}
}
