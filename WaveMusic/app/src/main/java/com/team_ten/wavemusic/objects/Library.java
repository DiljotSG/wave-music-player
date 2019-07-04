package com.team_ten.wavemusic.objects;

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
		if (newLibrary == null)
		{
			System.out.println("[!] Tried setting full library to null; ignoring request");
		}
		else
		{
			fullSongLibrary = newLibrary;
		}
	}

	/**
	 * Set the current list of library songs; generally the songs required to populate the next or
	 * current view.
	 */
	public static void setCurSongLibrary(ArrayList<Song> newLibrary)
	{
		if (newLibrary == null)
		{
			System.out.println("[!] Tried setting current library to null; ignoring request");
		}
		else
		{
			curSongLibrary = newLibrary;
		}
	}

	/**
	 * Set the current set of strings representing the Library, for example for song names.
	 */
	public static void setCurStringLibrary(ArrayList<String> newLibrary)
	{
		if (newLibrary == null)
		{
			System.out.println("[!] Tried setting current library to null; ignoring request");
		}
		else
		{
			curStringLibrary = newLibrary;
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

