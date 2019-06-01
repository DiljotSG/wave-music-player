package com.team_ten.wavemusic;

// An individual song with all of a song's properties.
public class Song
{
	private String name;
	private String artist;
	private String album;
	private String playlist;
	private String URI;

	Song(String songName, String songArtist, String songAlbum, String songPlaylist, String songURI)
	{
		name = songName;
		artist = songArtist;
		album = songAlbum;
		playlist = songPlaylist;
		URI = songURI;
	}

	/**
	 * Purpose: Get the name of the song.
	 *
	 * @return A string value that is the name of the song.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Purpose: Get the artist of the song.
	 *
	 * @return A string value that is the artist of the song.
	 */
	public String getArtist()
	{
		return artist;
	}

	/**
	 * Purpose: Get the album of the song.
	 *
	 * @return A string value that is the album of the song.
	 */
	public String getAlbum()
	{
		return album;
	}

	/**
	 * Purpose: Get the playlist the song belongs to.
	 *
	 * @return A string value that is the Playlist of the song.
	 */
	public String getPlaylist()
	{
		return playlist;
	}

	/**
	 * Purpose: Get the URI of the song.
	 *
	 * @return A string value that is the URI of the song.
	 */
	public String getURI()
	{
		return URI;
	}

	/**
	 * Purpose: Set the name of the song.
	 *
	 * @param songName A string value that is the name of the song.
	 */
	public void setName(String songName)
	{
		name = songName;
	}

	/**
	 * Purpose: Set the artist of the song.
	 *
	 * @param songArtist A string value that is the name of the artist.
	 */
	public void setArtist(String songArtist)
	{
		artist = songArtist;
	}

	/**
	 * Purpose: Set the album of the song.
	 *
	 * @param songAlbum A string value that is the name of the album.
	 */
	public void setAlbum(String songAlbum)
	{
		album = songAlbum;
	}

	/**
	 * Purpose: Set the playlist that the song belongs to.
	 *
	 * @param songPlaylist A string value that is the name of the playlist.
	 */
	public void setPlaylist(String songPlaylist)
	{
		playlist = songPlaylist;
	}

	/**
	 * Purpose: Set the URI of the song.
	 *
	 * @param songURI A string value that is the location of the song on disk.
	 */
	public void setURI(String songURI)
	{
		URI = songURI;
	}
}
