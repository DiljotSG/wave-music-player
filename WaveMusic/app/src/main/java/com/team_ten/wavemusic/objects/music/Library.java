package com.team_ten.wavemusic.objects.music;

import com.team_ten.wavemusic.objects.exceptions.WaveEmptyLibraryException;

import java.util.ArrayList;

public class Library
{
	private static ArrayList<Song> curSongLibrary = new ArrayList<>();
	private static ArrayList<String> curStringLibrary = new ArrayList<>();

	/**
	 * Set the current list of library songs; generally the songs required to populate the next or
	 * current view.
	 */
	public static void setCurSongLibrary(ArrayList<Song> newLibrary)
	{
		if (newLibrary != null)
		{
			curSongLibrary = newLibrary;
		}
		else
		{
			throw new WaveEmptyLibraryException("Tried setting current library to null.");
		}
	}

	/**
	 * Set the current set of strings representing the Library, for example for song names.
	 */
	public static void setCurStringLibrary(ArrayList<String> newLibrary)
	{
		if (newLibrary != null)
		{
			curStringLibrary = newLibrary;
		}
		else
		{
			throw new WaveEmptyLibraryException("Tried setting current library to null.");
		}
	}

	/**
	 * Allows you to get a string list of the current libary.
	 *
	 * @return The current libary as strings.
	 */
	public static ArrayList<String> getCurStringLibrary()
	{
		return curStringLibrary;
	}

	/**
	 * Allows you to get the current library as a list of songs.
	 *
	 * @return The current library as a list of songs
	 */
	public static ArrayList<Song> getCurSongLibrary()
	{
		return curSongLibrary;
	}
}