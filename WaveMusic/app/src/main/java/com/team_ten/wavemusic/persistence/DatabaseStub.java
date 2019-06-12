package com.team_ten.wavemusic.persistence;

import com.team_ten.wavemusic.objects.Song;

import java.util.ArrayList;

public class DatabaseStub implements IDatabaseController
{
	private ArrayList<Song> songList;

	/**
	 * Contructor for a database stub.
	 */
	public DatabaseStub()
	{
		songList = new ArrayList<>();
	}

	/***
	 * Add a song to the database.
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
	 * Get all songs from the specified artist.
	 *
	 * @param songArtist The name of the artist.
	 *
	 * @return An ArrayList of Songs from the specified artist.
	 */
	public ArrayList<Song> getSongsFromArtist(String songArtist)
	{
		ArrayList<Song> result = new ArrayList<>();

		if (hasLibrary())
		{
			for (int i = 0; i < songList.size(); i++)
			{
				Song currSong = songList.get(i);

				if ((currSong.getArtist()).equals(songArtist))
				{
					result.add(currSong);
				}
			}
		}

		return result;
	}

	/***
	 * Get all songs from the specified album.
	 *
	 * @param songAlbum The name of the album.
	 *
	 * @return An ArrayList of Songs from the specified album.
	 */
	public ArrayList<Song> getSongsFromAlbum(String songAlbum)
	{
		ArrayList<Song> result = new ArrayList<>();

		if (hasLibrary())
		{
			for (int i = 0; i < songList.size(); i++)
			{
				Song currSong = songList.get(i);

				if ((currSong.getAlbum()).equals(songAlbum))
				{
					result.add(currSong);
				}
			}
		}

		return result;
	}

	/**
	 * Get the specified song from the library.
	 *
	 * @param songName Name of the song to get.
	 *
	 * @return An ArrayList of matching Songs.
	 */
	public ArrayList<Song> getSong(String songName)
	{
		ArrayList<Song> result = new ArrayList<>();

		if (hasLibrary())
		{
			for (int i = 0; i < songList.size(); i++)
			{
				Song currSong = songList.get(i);

				if ((currSong.getName()).equals(songName))
				{
					result.add(currSong);
				}
			}
		}

		return result;
	}

	/**
	 * Get the song at the specified index.
	 *
	 * @param index The index in songList to get the song from.
	 *
	 * @return The Song at the specified index.
	 */
	public Song getSong(int index)
	{
		// Return the song only if the position parameter is valid.
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
	 * Get the library of songs.
	 *
	 * @return An ArrayList of Songs.
	 */
	public ArrayList<Song> getLibrary()
	{
		return songList;
	}

	/**
	 * Checks if the library exists.
	 *
	 * @return A Boolean specifying if the library exists or does not exist.
	 */
	public Boolean hasLibrary()
	{
		return songList.size() != 0;
	}
}