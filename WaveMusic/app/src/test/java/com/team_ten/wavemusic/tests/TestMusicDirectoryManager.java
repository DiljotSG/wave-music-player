package com.team_ten.wavemusic.tests;

import com.team_ten.wavemusic.application.MusicDirectoryManager;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class TestMusicDirectoryManager
{
	@Test public void TestDirectoryManager()
	{
		// This folder should be empty...
		// Or we don't have permission to read it.
		String extPath = "/";

		MusicDirectoryManager test = new MusicDirectoryManager(extPath);

		// There is no next song.
		assertFalse("There should be no songs in this directory.", test.hasNext());

		// Getting the next song, should give null.
		assertNull("Asking for the next song should not work.", test.getNextSong());
	}
}