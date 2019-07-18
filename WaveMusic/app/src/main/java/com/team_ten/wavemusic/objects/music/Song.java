package com.team_ten.wavemusic.objects.music;

import java.io.Serializable;

// An individual song with all of a song's properties.
public class Song implements Serializable
{
	// Instance variables.
	private String name;
	private String artist;
	private String album;
	private String URI;
	private String genre;
	private int playCount;

	/**
	 * Constructor for a song.
	 *
	 * @param songName   The Song's name.
	 * @param songArtist The Song's Artist.
	 * @param songAlbum  The Song's Album.
	 * @param songURI    The Song's URI.
	 * @param songGenre  The Song's genre.
	 */
	public Song(String songName, String songArtist, String songAlbum, String songURI, String songGenre, int count)
	{
		name = songName;
		artist = songArtist;
		album = songAlbum;
		URI = songURI;
		genre = songGenre;
		playCount = count;

		if (artist == null)
		{
			artist = "Unknown Artist";
		}
		if (album == null)
		{
			album = "Unknown Album";
		}
		if (name == null)
		{
			name = "Untitled";
		}
	}

	/**
	 * Get the name of the song.
	 *
	 * @return A string value that is the name of the song.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Set the name of the song.
	 *
	 * @param songName A string value that is the name of the song.
	 */
	public void setName(String songName)
	{
		name = songName;
	}

	/**
	 * Get the artist of the song.
	 *
	 * @return A string value that is the artist of the song.
	 */
	public String getArtist()
	{
		return artist;
	}

	/**
	 * Set the artist of the song.
	 *
	 * @param songArtist A string value that is the name of the artist.
	 */
	public void setArtist(String songArtist)
	{
		artist = songArtist;
	}

	/**
	 * Get the album of the song.
	 *
	 * @return A string value that is the album of the song.
	 */
	public String getAlbum()
	{
		return album;
	}

	/**
	 * Set the album of the song.
	 *
	 * @param songAlbum A string value that is the name of the album.
	 */
	public void setAlbum(String songAlbum)
	{
		album = songAlbum;
	}

	/**
	 * Get the URI of the song.
	 *
	 * @return A string value that is the URI of the song.
	 */
	public String getURI()
	{
		return URI;
	}

	/**
	 * Set the URI of the song.
	 *
	 * @param songURI A string value that is the location of the song on disk.
	 */
	public void setURI(String songURI)
	{
		URI = songURI;
	}

	/**
	 * Get the genre of the song.
	 *
	 * @return A string value that is the genre of the song.
	 */
	public String getGenre()
	{
		return genre;
	}

	/**
	 * Returns the play count of the song
	 */
	public int getPlayCount()
	{
		return playCount;
	}

	/**
	 * To print the information of the Song object FOR USER, not including URI.
	 *
	 * @return String A String including title, artist and album of this song object.
	 */
	@Override public String toString()
	{
		return name + "\n" + artist + " - " + album;
	}

	/**
	 * To check if 2 Song objects are equal.
	 *
	 * @return boolean To return true if they are equal, false otherwise.
	 */
	@Override public boolean equals(Object other)
	{
		if (other instanceof Song)
		{
			// Two Song objects are equal if they have the same URI.
			return ((Song) (other)).getURI().equals(this.getURI());
		}
		return false;
	}
}