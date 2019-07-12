package com.team_ten.wavemusic.objects;

import com.team_ten.wavemusic.objects.Exceptions.WaveEmptyLibraryException;

import java.util.ArrayList;

public class Library
{
	private static ArrayList<Song> fullSongLibrary = new ArrayList<>();
	private static ArrayList<Song> curSongLibrary = new ArrayList<>();
	private static ArrayList<String> curStringLibrary = new ArrayList<>();

	/**
	 * Set the current list of songs which describes the full application music library.
	 */
	public static void setFullSongLibrary(ArrayList<Song> newLibrary)
	{
		if (newLibrary != null)
		{
			fullSongLibrary = newLibrary;
		}
		else
		{
			throw new WaveEmptyLibraryException("Tried setting current library to null.");
		}
	}

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

	public static ArrayList<Song> getFullSongLibrary()
	{
		return fullSongLibrary;
	}

	public static ArrayList<String> getCurStringLibrary()
	{
		return curStringLibrary;
	}

	public static ArrayList<Song> getCurSongLibrary()
	{
		return curSongLibrary;
	}
}

