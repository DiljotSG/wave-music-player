package com.team_ten.wavemusic;

// Import statements

import java.util.ArrayList;

public class PlayList
{
	// Instance variables
	private String title;            // This is the title for the playlist
	private ArrayList<Song> list;    // This is a list of all songs in the playlist.

	/**
	 * Purpose: Constructor.
	 *
	 * @param listTitle The title of the new PlayList object to be created.
	 */
	public PlayList(String listTitle)
	{
		title = listTitle;
		list = new ArrayList<>();
	}

	/**
	 * Purpose: Add a song to the playlist if it is not in the playlist at that time.
	 *
	 * @param newSong The song to be added into the playlist.
	 * @return boolean To return true if the adding has been done successfully, false otherwise.
	 */
	public boolean add(Song newSong)
	{
		boolean exists = list.contains(newSong);

		// To add the song if it is not in the playlist, otherwise do nothing.
		if (!exists)
		{
			list.add(newSong);
		}

		return !exists;
	}

	/**
	 * Purpose: To get the title of this playlist.
	 *
	 * @return String The title of this playlist.
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Purpose: To get the list of this playlist.
	 *
	 * @return ArrayList<Song> The list of this playlist.
	 */
	public ArrayList<Song> getList()
	{
		return list;
	}

	/**
	 * Purpose: To get the amount of songs in this playlist.
	 *
	 * @return int The amount of songs in this playlist.
	 */
	public int getSize()
	{
		return list.size();
	}

	/**
	 * Purpose: To get a song based on its position in the playlist.
	 *
	 * @param position The position of the song to be searched in the playlist.
	 * @return Song The song found.
	 */
	public Song getSong(int position)
	{
		// To return the song only if the position parameter is valid.
		if(position < 0 || position >= list.size())
		{
			return null;
		}
		else
		{
			return list.get(position);
		}
	}
}