package com.team_ten.wavemusic.persistence;

import com.team_ten.wavemusic.objects.Song;

import java.util.ArrayList;

public class DatabaseStub implements DatabaseController
{
	private ArrayList<Song> songList;

	public DatabaseStub()
	{
		songList = new ArrayList<>();
	}

	/***
	 * Purpose: Add a song to the database.
	 *
	 * @param song Song to add to the database.
	 */
	public void addSong(Song song)
	{
		if (!songList.contains(song))
		{
			songList.add(song);
		}
	}

	/**
	 * Purpose: Get all songs from the specified artist.
	 *
	 * @param artist The name of the artist.
	 *
	 * @return An ArrayList of Songs from the specified artist.
	 */
	public ArrayList<Song> getSongsFromArtist(String artist)
	{
		ArrayList<Song> result = new ArrayList<>();

		if (hasLibrary())
		{
			for (int i = 0; i < songList.size(); i++)
			{
				String temp = songList.get(i).getArtist();

				if (temp.equals(artist))
				{
					result.add(songList.get(i));
				}
			}
		}

		return result;
	}

	/***
	 * Purpose: Get all songs from the specified album.
	 *
	 * @param album The name of the album.
	 * @return An ArrayList of Songs from the specified album.
	 */
	public ArrayList<Song> getSongsFromAlbum(String album)
	{
		ArrayList<Song> result = new ArrayList<>();

		if (hasLibrary())
		{
			for (int i = 0; i < songList.size(); i++)
			{
				String temp = songList.get(i).getAlbum();

				if (temp.equals(album))
				{
					result.add(songList.get(i));
				}
			}
		}

		return result;
	}

	/**
	 * Purpose: Get the specified song from the library.
	 *
	 * @param songName Name of the song to get.
	 *
	 * @return The matching Song or null if the song is not found.
	 */
	public Song getSong(String songName)
	{
		Song result = null;

		if (hasLibrary())
		{
			for (int i = 0; i < songList.size(); i++)
			{
				String temp = songList.get(i).getName();
				if (temp.equals(songName))
				{
					result = songList.get(i);
					break;
				}
			}
		}

		return result;
	}

	/**
	 * Purpose: Get the song at the specified index
	 *
	 * @param index The index in songList to get the song from.
	 *
	 * @return The Song at the specified index.
	 */
	public Song getSong(int index)
	{
		// To return the song only if the position parameter is valid.
		if (index < 0 || index >= songList.size())
		{
			return null;
		}
		else
		{
			return songList.get(index);
		}
	}

	/**
	 * Purpose: Get the library of songs.
	 *
	 * @return An ArrayList of Songs.
	 */
	public ArrayList<Song> getLibrary()
	{
		return songList;
	}

	/**
	 * Purpose: Checks if the library exists.
	 *
	 * @return A Boolean specifying if the library exists or does not exist.
	 */
	public Boolean hasLibrary()
	{
		return songList.size() != 0;
	}
}
