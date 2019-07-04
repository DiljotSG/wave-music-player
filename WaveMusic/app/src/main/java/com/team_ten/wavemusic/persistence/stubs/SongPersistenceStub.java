package com.team_ten.wavemusic.persistence.stubs;

import com.team_ten.wavemusic.objects.Song;
import com.team_ten.wavemusic.persistence.ISongPersistence;

import java.util.ArrayList;

public class SongPersistenceStub implements ISongPersistence
{
	private ArrayList<Song> songList;

	public SongPersistenceStub()
	{
		songList = new ArrayList<>();
	}

	/**
	 * Adds a Song to the DB.
	 *
	 * @param theSong The song to add.
	 */
	public void addSong(Song theSong)
	{
		if (!songList.contains(theSong))
		{
			songList.add(theSong);
		}
	}

	/**
	 * Removes a Song from the user's library.
	 *
	 * @param toRemove The song to remove from the user's library.
	 */
	public void removeSong(Song toRemove)
	{
		songList.remove(toRemove);
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
		Song result = null;
		for (Song curr : songList)
		{
			if (curr.getURI().equalsIgnoreCase(songURI))
			{
				result = curr;
				break;
			}
		}
		return result;
	}

	/**
	 * Gets every song in the library.
	 *
	 * @return Returns an ArrayList of every Song.
	 */
	public ArrayList<Song> getAllSongs()
	{
		return songList;
	}

	/**
	 * Gets all of the artists names.
	 *
	 * @return An array list of artists names
	 */
	public ArrayList<String> getAllArtists()
	{
		ArrayList<String> artists = new ArrayList<>();
		for (Song curr : songList)
		{
			if (!artists.contains(curr.getArtist()))
			{
				artists.add(curr.getArtist());
			}
		}
		return artists;
	}

	/**
	 * Gets all of the album names.
	 *
	 * @return An array list of album names
	 */
	public ArrayList<String> getAllAlbums()
	{
		ArrayList<String> albums = new ArrayList<>();
		for (Song curr : songList)
		{
			if (!albums.contains(curr.getAlbum()))
			{
				albums.add(curr.getAlbum());
			}
		}
		return albums;
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
		ArrayList<Song> result = new ArrayList<>();
		for (Song curr : songList)
		{
			if (curr.getAlbum().equalsIgnoreCase(albumName))
			{
				result.add(curr);
			}
		}
		return result;
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
		ArrayList<Song> result = new ArrayList<>();
		for (Song curr : songList)
		{
			if (curr.getArtist().equalsIgnoreCase(artistName))
			{
				result.add(curr);
			}
		}
		return result;
	}

}
